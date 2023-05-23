<template>
  <div class="background">
    <main-layout></main-layout>
    <div class="layouts">
      <vue-good-table
          :columns="columns"
          :rows="buses"
          class="table">

        <template slot="table-row" slot-scope="props">
                  <span v-if="props.column.field == 'before'">
                    <b-button squared variant="primary" @click="show(props.row.id)">Изменить</b-button>
                    <b-button squared variant="danger"  @click="setId(props.row.id)" v-b-modal.modal-delete-bus>Удалить</b-button>
                  </span>
          <span v-else>
                      {{ props.formattedRow[props.column.field] }}
                  </span>
        </template>

      </vue-good-table>
    </div>

    <!-- Styled -->
    <div class="layouts">
      <b-button variant="primary" @click="showForAdd">Добавить</b-button>
      <p></p>

      <b-button pill variant="secondary" @click="getJSON">Выгрузить в формате .JSON</b-button>
      <b-button pill variant="secondary" @click="generatePDF">Выгрузить в формате .PDF</b-button>
    </div>

    <b-form inline class="layouts" id="form">
      <label class="sr-only" for="inline-form-input-name">Name</label>

      <b-form-input readonly
                    v-model="id"
                    id="inline-form-input-name"
                    class="mb-2 mr-sm-2 mb-sm-0"
                    placeholder="Номер"
      ></b-form-input>

      <b-form-input
          v-model="number"
          id="inline-form-input-name"
          class="mb-2 mr-sm-2 mb-sm-0"
          placeholder="Номер автобуса"
      ></b-form-input>

      <b-form-input
          v-model="start"
          id="inline-form-input-name"
          class="mb-2 mr-sm-2 mb-sm-0"
          placeholder="Время начала маршрута"
      ></b-form-input>

      <b-form-input
          v-model="end"
          id="inline-form-input-name"
          class="mb-2 mr-sm-2 mb-sm-0"
          placeholder="Время конца маршрута"
      ></b-form-input>

      <b-form-input
          v-model="driverId"
          id="inline-form-input-name"
          class="mb-2 mr-sm-2 mb-sm-0"
          placeholder="Водитель"
      ></b-form-input>

      <b-button variant="primary" id="formUpdate" @click="updateBus">Сохранить</b-button>

      <b-button variant="primary" id="formAdd" @click="addBus">Сохранить</b-button>

      <b-button pill variant="danger" @click="clean">Закрыть</b-button>
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
        id="modal-delete-bus"
        ref="modal"
        title="Подтвердите удаление автобуса"
        @ok="deleteBus"
    >
      Удалить автобус?
    </b-modal>

  </div>

</template>

<script>


import MainLayout from "@/layouts/MainLayout.vue";
import {url} from "@/main";
import jsPDF from "jspdf";
import font from "@/Comic Sans MS-normal";

export default {
  name: 'BusesView',
  components: {MainLayout},
  data() {
    return {
      file: null,
      dismissSecs: 3,
      dismissCountDown: 0,
      showDismissibleAlert: false,
      id: null,
      number: null,
      start: null,
      end: null,
      driver: null,
      driverId: null,
      alertText: "Ошибка!",
      columns: [
        {
          label: 'Номер',
          field: 'id'
        },
        {
          label: 'Номер автобуса',
          field: 'number',
        },
        {
          label: 'Начало работы автобуса',
          field: 'start',
          type: 'number',
        },
        {
          label: 'Конец работы автобуса',
          field: 'end',
          type: 'number',
        },
        {
          label: 'Водитель',
          field: 'driver.id',
          type: 'number',
        },
        {
          label: 'Действия',
          field: 'before',
          sortable: false,
        }
      ],
      buses: [],
    }
  },
  created() {
    this.getData()
  },
  methods: {
    getData() {
      this.$http.get(url + "/bus")
          .then(response => {
            const buses = response && response.data ? response.data : []
            //localStorage.setItem('drivers', JSON.stringify(drivers))
            this.buses = buses.map(r => {
              return {
                id: r.id,
                number: r.number,
                start: r.start,
                end: r.end,
                driver: r.driver,
              }
            })
          }).catch(e => {
        console.log(e)
      })
    },
    createData() {
      if (this.driverId != null && this.driverId !== '') {
      this.$http.get(url + "/driver/" + this.driverId)
          .then(response => {
                const driver = response && response.data ? response.data : []
                //localStorage.setItem('drivers', JSON.stringify(drivers))
                console.log("Driver: " + this.driver)
                this.$http.post(url + "/bus", {
                  number: this.number,
                  start: this.start,
                  end: this.end,
                  driver: driver,
                }).catch(() => {
                  this.alertText = "Ошибка!"
                }).then(() => this.getData())
              }
          ).catch(e => {
        console.log(e)
      }).then(() => {

      })
      } else {
        this.$http.post(url + "/bus", {
          number: this.number,
          start: this.start,
          end: this.end,
        }).catch(() => {
          this.alertText = "Ошибка!"
        }).then(() => this.getData())
      }

    },
    updateData() {
      if (this.driverId != null && this.driverId !== '') {
        this.$http.get(url + "/driver/" + this.driverId)
            .then(response => {
                  const driver = response && response.data ? response.data : []
                  //localStorage.setItem('drivers', JSON.stringify(drivers))
                  console.log("Driver: " + this.driver)
                  this.$http.patch(url + "/bus/" + this.id.toString(), {
                    number: this.number,
                    start: this.start,
                    end: this.end,
                    driver: driver,
                  }).catch(() => {
                    this.alertText = "Ошибка!"
                  }).then(() => this.clean()).then(() => this.getData())
                }
            ).catch(e => {
          console.log(e)
        }).then(() => {

        })
      } else {
        this.$http.patch(url + "/bus/" + this.id.toString(), {
          number: this.number,
          start: this.start,
          end: this.end,
        }).catch(() => {
          this.alertText = "Ошибка!"
        }).then(() => this.clean()).then(() => this.getData())
      }

      /*
      this.$http.patch(url + "/bus/" + this.id.toString(), {
            id: this.id,
            number: this.number,
            start: this.start,
            end: this.end,
            driver: this.driver,
          }
      ).catch((e) => {
        console.log(e.toString())
        this.alertText = "Ошибка!"
      }).then(() => this.getData())
      */

    },
    deleteData() {
      this.$http.delete(url + "/bus/" + this.id.toString()).catch((e) => {
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
    addBus() {
      this.createData()
      this.clean()
      this.showAlert("Добавлено!")

    },
    updateBus() {
      this.updateData()
      this.showAlert("Обновлено!")
    },
    deleteBus() {
      this.deleteData()
      this.showAlert("Удалено!")
    },
    getJSON() {
      var fileDownload = require('js-file-download');
      fileDownload(JSON.stringify(this.buses), 'buses.json');
      this.showAlert("Скачано!")
    },
    addJSON() {
      let reader = new FileReader()
      reader.readAsText(this.file)

      reader.onload = () => {
        let data = JSON.parse(reader.result)

        this.$http.post(url + "/bus/list", data).catch(() => {
          this.alertText = "Ошибка!"
        }).then(() => this.getData())
      }
      this.showAlert("Загружено!")
    },
    getIndex(list, id) {
      for (let i = 0; i < list.length; i++) {
        if (list[i].id === id) {
          return i;
        }
      }
    },
    show(id) {
      const index = this.getIndex(this.buses, id);
      document.getElementById("form").style.display = "block"
      document.getElementById("formAdd").style.display = "none"
      document.getElementById("formUpdate").style.display = "inline-block"
      this.start = this.buses[index].start;
      this.id = this.buses[index].id;
      this.number = this.buses[index].number;
      this.end = this.buses[index].end;
      if (this.buses[index].driver != null) {
        this.driverId = this.buses[index].driver.id;
      } else {
        this.driverId = null;
      }
    },
    showForAdd() {
      this.start = null;
      this.id = null;
      this.number = null;
      this.end = null;
      this.driverId = null;
      document.getElementById("form").style.display = "block"
      document.getElementById("formUpdate").style.display = "none"
      document.getElementById("formAdd").style.display = "inline-block"
    },
    clean() {
      document.getElementById("form").style.display = "none";
      this.start = null;
      this.id = null;
      this.end = null;
      this.driverId = null;
    },
    generatePDF() {
      const pdf = new jsPDF()
      console.log(pdf.getFontList())

      const myFont = font;

      // add the font to jsPDF
      pdf.addFileToVFS("MyFont.ttf", myFont);
      pdf.addFont("MyFont.ttf", "MyFont", "normal");
      pdf.setFont("MyFont");

      let text = "Автобусы\n";
      let driverText;
      for (let i = 0; i < this.buses.length; i++) {
        if (this.buses[i].driver != null) {
          driverText = this.buses[i].driver.name;
        } else {
          driverText = "Без водителя";
        }

        text += this.buses[i].id + ") Номер: " + this.buses[i].number + ". Начало: " + this.buses[i].start + ". Окончание: " + this.buses[i].end + ". Водитель: " + driverText + ".\n"
      }
      pdf.text(text, 10, 10)
      pdf.save("buses.pdf")
      this.showAlert("Скачано!")
    },
    setId(id) {
      this.id = id;
    }
  }
}
</script>

<style>
.layouts {
  padding: 10px 20px 30px 40px;
}

.background {
  background-image: url('\\img\\bus5.jpg');
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
