<template>
  <div class="background">
    <main-layout></main-layout>

    <div class="layouts">
      <vue-good-table
          id="table"
          ref="my-table"

          :columns="columns"
          :rows="routes"

          :pagination-options="{
            enabled: true,
            perPageDropdown: [3, 5, 10],
            rowsPerPageLabel: 'Строк на страницу',
            nextLabel: 'След.',
            prevLabel: 'Пред.',
            ofLabel: 'из',}"

          :select-options="{
            selectionText: 'выделено',
            clearSelectionText: 'Очистить',
            enabled: true,}"

          :search-options="{
            enabled: true,
            placeholder: 'Поиск по номеру и номеру маршрута',
      }">
        <div slot="selected-row-actions">
          <button  @click="showSelectedRows">Показать только выделенные</button>
        </div>

        class="table">


        <template slot="table-row" slot-scope="props">
                  <span v-if="props.column.field == 'before'">
                     <b-button-group>
                    <b-button squared variant="primary" @click="show(props.row.id)">Изменить</b-button>
                    <b-button squared variant="danger" @click="setId(props.row.id)"
                              v-b-modal.modal-delete-route>Удалить</b-button>

                    <b-button variant="danger" @click="setId(props.row.id)" v-b-modal.modal-remove-violations>Очистить графу нарушений</b-button>
                       </b-button-group>
                  </span>


          <span v-else-if="props.column.field == 'violations'">
                     <b-card
                         title=""
                         tag="article"
                         style="max-width: 70rem"
                         class="mb-2"
                     >
                      <b-card-text>
                        <li v-for="violation in props.row.violations" v-bind:key="violation.id">
                            {{ violation }}
                         </li>
                      </b-card-text>

                    </b-card>
            </span>

          <span v-else-if="props.column.field == 'buses'">
                     <b-card
                         title=""
                         tag="article"
                         style="max-width: 70rem"
                         class="mb-2"
                     >
                      <b-card-text>
                        <li v-for="bus in props.row.buses" v-bind:key="bus.id">
                            {{
                            "Номер: " + bus.id + " Номер автобуса: " + bus.number + " Начало: " + bus.start + " Конец: " + bus.end
                          }}
                            <div v-if="bus.driver != null">
                            {{ "Имя водителя: " + bus.driver.name }}
                              </div>
                         </li>
                      </b-card-text>

                    </b-card>
            </span>


          <span v-else>
                      {{ props.formattedRow[props.column.field] }}
            </span>

        </template>

        <div slot="emptystate">
          <div class="d-flex justify-content-center mb-3">
            <b-spinner label="Loading..."></b-spinner>
          </div>
        </div>

      </vue-good-table>
    </div>

    <div class="layouts">
      <b-button variant="primary" @click="showForAdd">Добавить</b-button>
      <p></p>
      <b-button pill variant="secondary" @click="getJSON">Выгрузить в формате .JSON</b-button>
      <b-button pill variant="secondary" @click="generatePDF">Выгрузить в формате .PDF</b-button>
    </div>


    <b-form inline class="layouts" id="form">
      <label class="sr-only" for="inline-form-input-name">Name</label>

      <b-form-input readonly
                    v-model="id1"
                    id="inline-form-input-name"
                    class="mb-2 mr-sm-2 mb-sm-0"
                    placeholder="Номер"
      ></b-form-input>

      <b-form-input
          v-model="number"
          id="inline-form-input-name"
          class="mb-2 mr-sm-2 mb-sm-0"
          placeholder="Номер маршрута"
      ></b-form-input>

      <b-button variant="primary" id="formUpdate" @click="updateRoute">Сохранить</b-button>

      <b-button variant="primary" id="formAdd" @click="addRoute">Сохранить</b-button>

      <p></p>
      <div v-if="updateCheck == 1">
        <b-form-input
            v-model="busId"
            id="inline-form-input-name"
            class="mb-2 mr-sm-2 mb-sm-0"
            placeholder="Автобус"

        ></b-form-input>

        <b-button variant="primary" @click="addBus">Добавить</b-button>
        <b-button variant="danger" @click="removeBus">Удалить</b-button>
        <p></p>

        <b-form-input
            v-model="violation"
            id="inline-form-input-name"
            class="mb-2 mr-sm-2 mb-sm-0"
            placeholder="Нарушение"

        ></b-form-input>

        <b-button variant="primary" @click="addViolation">Добавить</b-button>
        <p></p>
      </div>
      <b-button pill variant="danger" @click="clean">Закрыть</b-button>

      <b-form-invalid-feedback :state="validationNumber">
        Номер маршрута должен быть цифрой больше 0.
      </b-form-invalid-feedback>
      <b-form-valid-feedback :state="validationNumber">
        Такой номер маршрута подходит.
      </b-form-valid-feedback>

    </b-form>


    <div class="layouts">
      <b-alert
          :show="dismissCountDown"
          dismissible
          variant="success"
          @dismissed="dismissCountDown=0"
          @dismiss-count-down="countDownChanged"
      >
        <p>{{ alertText }} {{ dismissCountDown }} </p>
        <b-progress
            variant="success"
            :max="dismissSecs"
            :value="dismissCountDown"
            height="4px"
        ></b-progress>
      </b-alert>
    </div>

    <b-modal
        id="modal-remove-violations"
        ref="modal"
        title="Подтвердите очистку графы нарушений"
        @ok="removeViolation"
    >
      Очистить графу нарушений?
    </b-modal>

    <b-modal
        id="modal-delete-route"
        ref="modal"
        title="Подтвердите удаление маршрута"
        @ok="deleteRoute"
    >
      Удалить маршрут?
    </b-modal>

  </div>
</template>

<script>

import MainLayout from "@/layouts/MainLayout.vue";
import {url} from "@/main";
import jsPDF from "jspdf";
import font from "@/Comic Sans MS-normal";

export default {
  name: 'ScheduleView',
  components: {MainLayout},
  data() {
    return {
      updateCheck: 0,
      id1: null,
      id2: null,
      id3: null,
      number: null,
      buses: null,
      violations: '',
      busId: null,
      violation: '',
      dismissSecs: 3,
      dismissCountDown: 0,
      showDismissibleAlert: false,
      alertText: "Ошибка!",
      columns: [
        {
          label: 'Номер',
          field: 'id',
          type: 'number'
        },
        {
          label: 'Номер маршрута',
          field: 'number',
          type: 'number',
        },
        {
          label: 'Автобусы',
          field: 'buses',
        },
        {
          label: 'Нарушения',
          field: 'violations',
        },
        {
          label: 'Действия',
          field: 'before',
          sortable: false
        },
      ],
      routes: [],

    }
  },
  created() {
    this.getData()
  },
  methods: {
    getData() {
      this.$http.get(url + "/route")
          .then(response => {
            const routes = response && response.data ? response.data : []
            //localStorage.setItem('drivers', JSON.stringify(drivers))
            this.routes = routes.map(r => {
              return {
                id: r.id,
                number: r.number,
                buses: r.buses,
                violations: r.violations
              }
            })
          }).catch(e => {
        console.log(e)
      })
    },
    createData() {
      this.$http.post(url + "/route", {
        number: this.number,
        buses: null,
        violations: null,
      }).catch(() => {
        this.alertText = "Ошибка!"
      }).then(() => this.getData())
    },
    updateData() {
      this.$http.patch(url + "/route/" + this.id1.toString(), {
            id: this.id1,
            number: this.number,
            buses: [],
            violations: [],
          }
      ).catch((e) => {
        console.log(e.toString())
        this.alertText = "Ошибка!"
      }).then(() => this.clean()).then(() => this.getData())
    },
    deleteData() {
      this.$http.delete(url + "/route/" + this.id1.toString()).catch((e) => {
        console.log(e.toString())
        this.alertText = "Ошибка!"
      }).then(() => this.getData())
    },
    countDownChanged(dismissCountDown) {
      this.dismissCountDown = dismissCountDown
    },
    showAlert(text) {
      this.alertText = text
      this.dismissCountDown = this.dismissSecs
    },
    addRoute() {
      if (this.validationNumberFoo()) {
        this.createData()
        this.clean()
        this.showAlert("Добавлено!")
      } else {
        this.showAlert("Некорректный ввод!")
      }

    },
    updateRoute() {
      if (this.validationNumberFoo()) {
        this.updateData()
        this.showAlert("Обновлено!")
      } else {
        this.showAlert("Некорректный ввод!")
      }
    },
    deleteRoute() {
      this.deleteData()
      this.showAlert("Удалено!")
    },
    addBus() {
      if (this.busId != null) {
        if (this.id1 != null) {
          this.$http.get(url + "/bus/" + this.busId.toString())
              .then(response => {
                const bus = response && response.data ? response.data : []
                //localStorage.setItem('drivers', JSON.stringify(drivers))
                this.$http.patch(url + "/route/addBusTo/" + this.id1.toString(),
                    bus
                ).catch((e) => {
                  console.log(e.toString())
                  this.alertText = "Ошибка!"
                }).then(() => this.clean()).then(() => this.getData())
              }).catch(e => {
                this.showAlert("Автобус не найден!")
            console.log(e)
          })
        } else {
          this.showAlert("Введите номер маршрута!")
        }
      } else {
        this.showAlert("Введите номер автобуса!")
      }
    },

    removeBus() {
      if (this.busId != null) {
        if (this.id1 != null) {
          this.$http.get(url + "/bus/" + this.busId.toString())
              .then(response => {
                const bus = response && response.data ? response.data : []
                //localStorage.setItem('drivers', JSON.stringify(drivers))
                this.$http.patch(url + "/route/removeBusFrom/" + this.id1.toString(),
                    bus
                ).catch((e) => {
                  console.log(e.toString())
                  this.alertText = "Ошибка!"
                }).then(() => this.getData())
              }).catch(e => {
            this.showAlert("Автобус не найден!")
            console.log(e)
          })
        } else {
          this.showAlert("Введите номер маршрута!")
        }
      } else {
        this.showAlert("Введите номер автобуса!")
      }
    },
    addViolation() {
      if (this.id1 != null) {
        this.$http.patch(url + "/route/addViolationTo/" + this.id1.toString(),
            {
              violation: this.violation,
            }
        ).catch((e) => {
          console.log(e.toString())
          this.alertText = "Ошибка!"
        }).then(() => this.clean()).then(() => this.getData())
        this.showAlert("Добавлено!")
      } else {
        this.showAlert("Введите номер!")
      }
    },

    removeViolation() {
      if (this.id1 != null) {
        this.$http.patch(url + "/route/removeViolationsFrom/" + this.id1.toString()
        ).catch((e) => {
          console.log(e.toString())
          this.alertText = "Ошибка!"
        }).then(() => this.getData())
        this.showAlert("Удалено!")
      } else {
        this.showAlert("Графа нарушений и так пуста!")
      }
    },

    getJSON() {
      var fileDownload = require('js-file-download');
      fileDownload(JSON.stringify(this.routes), 'routes.json');
      this.showAlert("Скачано!")
    },
    addJSON() {
      let reader = new FileReader()
      reader.readAsText(this.file)

      reader.onload = () => {
        let data = JSON.parse(reader.result)
        this.$http.post(url + "/route/list", data).catch(() => {
          this.alertText = "Ошибка!"
        }).then(() => this.getData())
      }
      this.showAlert("Загружено!")
    },
    generatePDF() {
      const pdf = new jsPDF()
      console.log(pdf.getFontList())

      const myFont = font;

      // add the font to jsPDF
      pdf.addFileToVFS("MyFont.ttf", myFont);
      pdf.addFont("MyFont.ttf", "MyFont", "normal");
      pdf.setFont("MyFont");

      let text = "Маршруты\n";

      for (let i = 0; i < this.routes.length; i++) {
        let violationsText = "";
        for (let j = 0; j < this.routes[i].violations.length; j++) {
          violationsText += "\n    -" + this.routes[i].violations[j];
        }
        text += this.routes[i].id + ") Номер автобуса: " + this.routes[i].number + ". Кол-во автобусов: " +
            this.routes[i].buses.length + " . Кол-во нарушений: " + this.routes[i].violations.length + ".\n";
        if (violationsText !== "") {
          text += "Нарушения: " + violationsText + "\n";
        }
      }
      pdf.text(text, 10, 10)
      pdf.save("routes.pdf")
      this.showAlert("Скачано!")
    },
    getIndex(list, id) {
      for (let i = 0; i < list.length; i++) {
        if (list[i].id === id) {
          return i;
        }
      }
    },
    show(id) {
      this.updateCheck = 1;
      const index = this.getIndex(this.routes, id);
      document.getElementById("form").style.display = "block"
      document.getElementById("formAdd").style.display = "none"
      document.getElementById("formUpdate").style.display = "inline-block"
      this.number = this.routes[index].number;
      this.id1 = this.routes[index].id;
      this.buses = this.routes[index].buses;
      this.violations = this.routes[index].violations;
      this.violation = "";
    },
    showForAdd() {
      this.updateCheck = 0;
      this.id1 = null;
      this.id2 = null;
      this.id3 = null;
      this.number = null;
      this.buses = null;
      this.violations = '';
      this.violation = "";
      document.getElementById("form").style.display = "block"
      document.getElementById("formUpdate").style.display = "none"
      document.getElementById("formAdd").style.display = "inline-block"
    },
    clean() {
      document.getElementById("form").style.display = "none";
      this.id1 = null;
      this.id2 = null;
      this.id3 = null;
      this.number = null;
      this.buses = null;
      this.violations = '';
      this.violation = "";
    },
    setId(id1) {
      this.id1 = id1;
    },
    showSelectedRows() {

      for (let i = 0; i <  this.$refs['my-table'].selectedRows.length; i++) {
        console.log(i + " " + this.$refs['my-table'].selectedRows[i]);
        this.$refs["my-table"]('getHiddenRows');
      }
    },
    validationNumberFoo() {
      return this.number > 0
    },
  },
  computed: {
    validationNumber() {
      return this.number > 0
    },
  },
}
</script>

<style>
.layouts {
  padding: 10px 20px 30px 40px;
}

.background {
  background-image: url('\\img\\route3.jpg');
  height: 100vh;
}

#form {
  display: none;
}

#formUpdate {
  display: none;
}

#formAdd {
  display: none;
}

.formFile {
  width: 75vh;
}

</style>