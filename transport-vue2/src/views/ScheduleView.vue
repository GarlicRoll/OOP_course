<template>
  <div class="background">
    <main-layout></main-layout>

    <div class="layouts">
      <vue-good-table
          @on-selected-rows-change="showExtraRows"
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
      <vue-good-table
          id = "tableExtra"
          :columns="columnsExtra"
                      :rows="extraTable"
                      :select-options="{
                         selectionText: '',
            clearSelectionText: '',
            enabled: false,}"
      >

        <div slot="selected-row-actions">
          <button  @click="showSelectedRows">Показать все</button>
        </div>
        <template slot="table-row" slot-scope="props">



          <span v-if="props.column.field == 'violations'">
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
            <h2>Выберите строчку в таблице сверху</h2>
          </div>
        </div>

      </vue-good-table>
    </div>

    <div class="layouts">
      <b-button id="buttonMain" variant="primary" @click="showForAdd">Добавить</b-button>
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
      extraTable : [],
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
      columnsExtra: [
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
        this.alertText = "Такой номер маршрута уже существует!"
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
        this.alertText = "Такой номер маршрута уже сущесвтует!"
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
      let errAdd = false;
      if (this.busId != null) {
        if (this.id1 != null) {
          this.$http.get(url + "/bus/" + this.busId.toString())
              .then(response => {
                const bus = response && response.data ? response.data : []
                //localStorage.setItem('drivers', JSON.stringify(drivers))
                this.$http.patch(url + "/route/addBusTo/" + this.id1.toString(),
                    bus
                ).catch((e) => {

                    errAdd = true;
                    this.showAlert("Такой автобус уже есть!")
                    console.log(e.toString())

                }).then(() => {this.getData(); if (!errAdd) {this.clean(); this.showAlert("Автобус добавлен!")}})
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
      let errDel = false;
      if (this.busId != null) {
        if (this.id1 != null) {
          this.$http.get(url + "/bus/" + this.busId.toString())
              .then(response => {
                const bus = response && response.data ? response.data : []
                //localStorage.setItem('drivers', JSON.stringify(drivers))
                this.$http.patch(url + "/route/removeBusFrom/" + this.id1.toString(),
                    bus
                ).catch((e) => {
                  errDel = true;
                  console.log(e.toString())
                  this.showAlert("Автобус не найден!")
                }).then(() => {this.getData(); if (errDel === false) {this.clean(); this.showAlert("Автобус удалён!")}})
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
      if (this.violation.trim().length > 0) {
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
      }
      else {
        this.showAlert("Введите нарушение!")
      }
    },

    removeViolation() {
      if (this.id1 != null) {
        this.$http.patch(url + "/route/removeViolationsFrom/" + this.id1.toString()
        ).catch((e) => {
          console.log(e.toString())
          this.alertText = "Графа нарушений и так пуста!"
        }).then(() => this.getData())
        this.showAlert("Удалено!")
      } else {
        this.showAlert("Графа нарушений и так пуста!")
      }
    },

    getJSON() {
      let rows = [];
      if (this.extraTable.length !== 0) {
        rows =  JSON.parse(JSON.stringify(this.extraTable))
        for (let i = 0; i < rows.length; i++) {
          delete rows[i]["vgt_id"];
          delete rows[i]["originalIndex"];
          delete rows[i]["vgtSelected"];
        }
      } else {
        rows = this.routes;
      }
      var fileDownload = require('js-file-download');
      fileDownload(JSON.stringify(rows), 'routes.json');
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
      let columns = [];
      if (this.extraTable.length !== 0) {
        columns = this.extraTable;
      } else {
        columns = this.routes;
      }
      for (let i = 0; i < columns.length; i++) {
        let violationsText = "";
        for (let j = 0; j < columns[i].violations.length; j++) {
          violationsText += "\n    -" + columns[i].violations[j];
        }
        text += columns[i].id + ") Номер автобуса: " + columns[i].number + ". Кол-во автобусов: " +
            columns[i].buses.length + " . Кол-во нарушений: " + columns[i].violations.length + ".\n";
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
      this.busId = null;
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
      this.busId = null;
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
      this.clean()
      if (document.getElementById("table").style.display === "none") {
        document.getElementById("table").style.display = "block"
        document.getElementById("tableExtra").style.display = "none"
        document.getElementById("buttonMain").style.display = "inline-block"
      } else {
        document.getElementById("tableExtra").style.display = "block"
        document.getElementById("table").style.display = "none"
        document.getElementById("buttonMain").style.display = "none"
      }
    },
    validationNumberFoo() {
      return this.number > 0
    },
    showExtraRows() {
      if (this.$refs['my-table'] === undefined) {
        this.extraTable = []
      } else {
        this.extraTable = this.$refs['my-table'].selectedRows
      }
    }
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

#tableExtra {
  display: none;
}

</style>