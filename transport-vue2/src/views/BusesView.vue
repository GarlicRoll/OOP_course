<template>
  <div class="background">
    <main-layout></main-layout>
    <div class="layouts">
    <vue-good-table
        :columns="columns"
        :rows="buses"
        class="table"></vue-good-table>
    </div>
    <b-form inline class="layouts">
      <label class="sr-only" for="inline-form-input-name">Name</label>

      <b-form-input
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

      <b-button variant="primary" @click="addBus">Добавить</b-button>
      <b-button variant="primary" @click="updateBus">Обновить</b-button>
      <b-button variant="primary" @click="deleteBus">Удалить</b-button>
      <b-button pill variant="secondary" @click="getJSON">Выгрузить в формате .JSON</b-button>
    </b-form>

    <!-- Styled -->
    <p></p>
    <form class="layouts">
    <b-form-file
        accept=".json"
        v-model="file"
        :state="Boolean(file)"
        placeholder="Выберите файл или перетащите сюда..."
        browse-text="Выбрать"
        drop-placeholder="Drop file here..."
    ></b-form-file>
      <p></p>
    <b-button pill variant="secondary" @click="addJSON">Звгрузить файл в формате .JSON</b-button>
    </form>
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
</template>

<script>


import MainLayout from "@/layouts/MainLayout.vue";
import {url} from "@/main";

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
      console.log("Driver: " + this.driverId)
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

        this.$http.post(url + "/bus", {
          number: this.number,
          start: this.start,
          end: this.end,
        }).catch(() => {
          this.alertText = "Ошибка!"
        }).then(() => this.getData())

      }).then(() => {

      })
    },
    updateData() {

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
                }).then(() => this.getData())
              }

          ).catch(e => {
        console.log(e)

        this.$http.patch(url + "/bus/" + this.id.toString(), {
          number: this.number,
          start: this.start,
          end: this.end,
        }).catch(() => {
          this.alertText = "Ошибка!"
        }).then(() => this.getData())

      }).then(() => {

      })
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

</style>
