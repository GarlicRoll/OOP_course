<template>
  <div class = "background">
    <main-layout></main-layout>
    <form class="layouts">
    <vue-good-table
        :columns="columns"
        :rows="drivers"
        class="table"></vue-good-table>
      </form>
    <b-form inline class="layouts">
      <label class="sr-only" for="inline-form-input-name">Name</label>

      <b-form-input
          v-model="id"
          id="inline-form-input-name"
          class="mb-2 mr-sm-2 mb-sm-0"
          placeholder="Номер"
      ></b-form-input>

      <b-form-input
          v-model="name"
          id="inline-form-input-name"
          class="mb-2 mr-sm-2 mb-sm-0"
          placeholder="Имя"
      ></b-form-input>

      <b-form-input
          v-model="age"
          id="inline-form-input-name"
          class="mb-2 mr-sm-2 mb-sm-0"
          placeholder="Возраст"
      ></b-form-input>

      <b-form-input
          v-model="experience"
          id="inline-form-input-name"
          class="mb-2 mr-sm-2 mb-sm-0"
          placeholder="Опыт"
      ></b-form-input>

      <b-form-input
          v-model="category"
          id="inline-form-input-name"
          class="mb-2 mr-sm-2 mb-sm-0"
          placeholder="Категория"
      ></b-form-input>

      <b-button variant="primary" @click="addDriver">Добавить</b-button>
      <b-button variant="primary" @click="updateDriver">Обновить</b-button>
      <b-button variant="primary" @click="deleteDriver">Удалить</b-button>
      <b-button pill variant="secondary" @click="getJSON">Выгрузить в формате .JSON</b-button>
      <b-button pill variant="secondary" @click="generatePDF">Выгрузить в формате .PDF</b-button>
    </b-form>

    <!-- Styled -->
    <p></p>
    <form class="layouts">
    <b-form-file
        accept=".json"
        v-model="file"
        :state="Boolean(file)"
        drop-placeholder="Drop file here..."
        placeholder="Выберите файл или перетащите сюда..."
        browse-text="Выбрать"
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

import {url} from "@/main";
import MainLayout from "@/layouts/MainLayout.vue";
import jsPDF from 'jspdf'
import font from '@/Comic Sans MS-normal';

export default {
  name: 'DriversView',
  components: {MainLayout},
  data() {
    return {
      file: null,
      dismissSecs: 3,
      dismissCountDown: 0,
      showDismissibleAlert: false,
      id: null,
      name: '',
      age: null,
      experience: null,
      alertText: "Ошибка!",
      category: '',
      columns: [
        {
          label: 'Номер',
          field: 'id'
        },
        {
          label: 'Имя',
          field: 'name',
        },
        {
          label: 'Возраст',
          field: 'age',
          type: 'number',
        },
        {
          label: 'Опыт',
          field: 'experience',
          type: 'number',
        },
        {
          label: 'Категория',
          field: 'category',
        },
      ],
      drivers: [],

    }
  },

  created() {
    this.getData()
    //this.rows = this.drivers
  },
  methods: {
    getData() {
      this.$http.get(url + "/driver")
          .then(response => {
            const drivers = response && response.data ? response.data : []
            //localStorage.setItem('drivers', JSON.stringify(drivers))
            this.drivers = drivers.map(r => {
              return {
                id: r.id,
                name: r.name,
                age: r.age,
                experience: r.experience,
                category: r.category
              }
            })
          }).catch(e => {
        console.log(e)
      })
    },
    createData() {
      this.$http.post(url + "/driver", {
        name: this.name,
        age: this.age,
        experience: this.experience,
        category: this.category,
      }).catch(() => {
        this.alertText = "Ошибка!"
      }).then(() => this.getData())
    },
    updateData() {
      this.$http.patch(url + "/driver/" + this.id.toString(), {
            id: this.id,
            name: this.name,
            age: this.age,
            experience: this.experience,
            category: this.category,
          }
      ).catch((e) => {
        console.log(e.toString())
        this.alertText = "Ошибка!"
      }).then(() => this.getData())
    },
    deleteData() {
      this.$http.delete(url + "/driver/" + this.id.toString()).catch((e) => {
        if (e.response.status === 404) {
          this.alertText = "Не найдено!"
          console.log("Error 404. Driver not found. WebApp. " + e.toString())
        } else {
          this.alertText = "Ошибка!"
          console.log("Error. WebApp. " + e.toString())
        }
      }).then(() => this.getData())
    },
    countDownChanged(dismissCountDown) {
      this.dismissCountDown = dismissCountDown
    },
    showAlert(text) {
      this.alertText = text
      this.dismissCountDown = this.dismissSecs
    },
    addDriver() {
      this.createData()
      this.showAlert("Добавлено!")
    },
    deleteDriver() {
      this.deleteData()
      this.showAlert("Удалено!")
    },
    updateDriver() {
      this.updateData()
      this.showAlert("Обновлено!")
    },
    getJSON() {
      var fileDownload = require('js-file-download');
      fileDownload(JSON.stringify(this.drivers), 'drivers.json');
      this.showAlert("Скачано!")
    },
    addJSON() {
      let reader = new FileReader()
      reader.readAsText(this.file)

      reader.onload = () => {
        let data = JSON.parse(reader.result)
        this.$http.post(url + "/driver/list", data).catch(() => {
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

      let text = "Водители\n";
      for (let i = 0; i < this.drivers.length; i++) {
        text += this.drivers[i].id + ") Имя: " + this.drivers[i].name + ". Возраст: " + this.drivers[i].age + " . Категория: " + this.drivers[i].category + ".\n"
      }
      pdf.text(text, 10, 10)
      pdf.save("drivers.pdf")
      this.showAlert("Скачано!")
    }
  },
};
</script>

<style>
.layouts {
  padding: 10px 20px 30px 40px;
}

.background {
  background-image: url('\\img\\driver2.jpg');
  height: 100vh;
}

</style>