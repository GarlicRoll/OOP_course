<template>
  <div class = "background">
    <main-layout></main-layout>

    <div class="layouts">
    <vue-good-table
        :columns="columns"
        :rows="drivers"
        class="table">

      <template slot="table-row" slot-scope="props">
                  <span v-if="props.column.field == 'before'">
                    <b-button squared variant="primary" @click="show(props.row.id)">Изменить</b-button>
                    <b-button squared variant="danger" @click="setId(props.row.id)" v-b-modal.modal-delete-driver>Удалить</b-button>
                  </span>
                  <span v-else>
                      {{props.formattedRow[props.column.field]}}
                  </span>
      </template>

      <div slot="emptystate">
        <div class="d-flex justify-content-center mb-3">
          <b-spinner label="Loading..."></b-spinner>
        </div>
      </div>

    </vue-good-table>
      </div>


    <!-- Styled -->
    <div class="layouts">
      <b-button variant="primary" @click="showForAdd">Добавить</b-button>
      <p></p>
    <b-form-file
        class="formFile"
        accept=".json"
        v-model="file"
        :state="Boolean(file)"
        drop-placeholder="Drop file here..."
        placeholder="Выберите файл или перетащите сюда..."
        browse-text="Выбрать"
    ></b-form-file>
      <p></p>

    <b-button pill variant="secondary" @click="addJSON">Загрузить файл в формате .JSON</b-button>
      <b-button pill variant="secondary" @click="getJSON">Выгрузить в формате .JSON</b-button>
      <b-button pill variant="secondary" @click="generatePDF">Выгрузить в формате .PDF</b-button>
    </div>

    <b-form  inline class="layouts" id="form">

      <label class="sr-only" for="inline-form-input-name">Name</label>

      <b-form-input readonly
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
      <b-button variant="primary" id="formUpdate" @click="updateDriver">Сохранить</b-button>

      <b-button variant="primary" id="formAdd" @click="addDriver">Сохранить</b-button>

        <b-button pill variant="danger" @click="clean">Закрыть</b-button>

      <b-form-invalid-feedback :state="validationName">
        Имя должно быть длиннее 3 символов и не являться цифрой.
      </b-form-invalid-feedback>
      <b-form-valid-feedback :state="validationName">
        Такое имя подходит.
      </b-form-valid-feedback>

      <b-form-invalid-feedback :state="validationAge">
      Возраст должен быть от 16 до 99.
      </b-form-invalid-feedback>
      <b-form-valid-feedback :state="validationAge">
        Такой возраст подходит.
      </b-form-valid-feedback>

      <b-form-invalid-feedback :state="validationExperience">
        Опыт должен быть меньше возраста минимум на 16 лет.
      </b-form-invalid-feedback>
      <b-form-valid-feedback :state="validationExperience">
        Такой опыт подходит.
      </b-form-valid-feedback>

      <b-form-invalid-feedback :state="validationCategory">
        Категория не должна быть пуста.
      </b-form-invalid-feedback>
      <b-form-valid-feedback :state="validationCategory">
        Такая категория подходит.
      </b-form-valid-feedback>

    </b-form>
    <div class="layouts">
    <b-alert class="layouts"
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
        id="modal-delete-driver"
        ref="modal"
        title="Подтвердите удаление водителя"
        @ok="deleteDriver"
    >
      Удалить водителя?
    </b-modal>

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
      conditionsDone: false,
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
        {
          label: 'Действия',
          field: 'before',
          sortable: false
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
      if (this.validationNameFoo() &&
      this.validationAgeFoo() &&
      this.validationExperienceFoo() &&
      this.validationCategoryFoo()) {
        this.createData()
        this.clean()
        this.showAlert("Добавлено!")
      } else {
        this.showAlert("Некорректный ввод!")
      }
    },
    deleteDriver() {
      this.deleteData()
      this.showAlert("Удалено!")
    },
    updateDriver() {
      if (this.validationNameFoo() &&
          this.validationAgeFoo() &&
          this.validationExperienceFoo() &&
          this.validationCategoryFoo()) {
          this.updateData()
          this.clean()
          this.showAlert("Обновлено!")
      } else {
        this.showAlert("Некорректный ввод!")
      }
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
    },
    getIndex(list, id) {
      for (let i = 0; i < list.length; i++ ) {
        if (list[i].id === id) {
          return i;
        }
      }
    },
    show(id) {
      const index = this.getIndex(this.drivers, id);
      document.getElementById("form").style.display = "block"
      document.getElementById("formAdd").style.display = "none"
      document.getElementById("formUpdate").style.display = "inline-block"
      this.name = this.drivers[index].name;
      this.id = this.drivers[index].id;
      this.age= this.drivers[index].age;
      this.category = this.drivers[index].category;
      this.experience= this.drivers[index].experience;
    },
    showForAdd() {
      this.id = null;
      this.name = '';
      this.age= null;
      this.category = '';
      this.experience= null;
      document.getElementById("form").style.display = "block"
      document.getElementById("formUpdate").style.display = "none"
      document.getElementById("formAdd").style.display = "inline-block"
    },
    clean() {
      document.getElementById("form").style.display = "none";
      this.id = null;
      this.name = '';
      this.age= null;
      this.category = '';
      this.experience= null;
    },
    setId(id) {
      this.id = id;
    },
    validationNameFoo() {
      return this.name.length > 3 && isNaN(this.name)
    },
    validationAgeFoo() {
      return this.age > 15 && this.age < 100
    },
    validationCategoryFoo() {
      return this.category.length > 0
    },
    validationExperienceFoo() {
      return this.experience < (this.age - 16)
    },

  },
  computed: {
    validationName() {
      return this.name.length > 3 && isNaN(this.name)
    },
    validationAge() {
      return this.age > 15 && this.age < 100
    },
    validationCategory() {
      return this.category.length > 0
    },
    validationExperience() {
      return this.experience < (this.age - 16)
    },
  }
};
</script>

<style>
.layouts {
  padding: 10px 20px 30px 40px;
}

.background {
  background-image: url('\\img\\driver3.jpg');
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