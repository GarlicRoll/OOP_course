<template>
    <div class="background">
        <main-layout></main-layout>
        <div class="layouts">
        <vue-good-table
                :columns="columns"
                :rows="routes"
                class="table">

          <template slot="table-row" slot-scope="props">
                  <span v-if="props.column.field == ''">
                     <b-card
                         title="Card Title"


                         tag="article"
                         style="max-width: 70rem;max-height: 7rem"
                         class="mb-2"
                     >
                      <b-card-text>
                        hi
                      </b-card-text>

                    </b-card>
                  </span>
            <span v-else>
                      {{props.formattedRow[props.column.field]}}
                  </span>
          </template>

        </vue-good-table>
        </div>
      <b-form inline class="layouts">
        <label class="sr-only" for="inline-form-input-name">Name</label>

        <b-form-input
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

        <b-button variant="primary" @click="addRoute">Добавить</b-button>
        <b-button variant="primary" @click="updateRoute">Обновить</b-button>
        <b-button variant="primary" @click="deleteRoute">Удалить</b-button>
      </b-form>

      <b-form inline class="layouts">
        <b-form-input
            v-model="id2"
            id="inline-form-input-name"
            class="mb-2 mr-sm-2 mb-sm-0"
            placeholder="Номер"
        ></b-form-input>

      <b-form-input
          v-model="busId"
          id="inline-form-input-name"
          class="mb-2 mr-sm-2 mb-sm-0"
          placeholder="Автобус"
      ></b-form-input>

        <b-button  variant="primary" @click="addBus">Добавить</b-button>
        <b-button  variant="primary" @click="removeBus">Удалить</b-button>
      </b-form >

      <b-form inline class="layouts">
        <b-form-input
            v-model="id3"
            id="inline-form-input-name"
            class="mb-2 mr-sm-2 mb-sm-0"
            placeholder="Номер"
        ></b-form-input>

        <b-form-input
            v-model="violation"
            id="inline-form-input-name"
            class="mb-2 mr-sm-2 mb-sm-0"
            placeholder="Нарушение"
        ></b-form-input>

        <b-button variant="primary" @click="addViolation">Добавить</b-button>
        <b-button variant="primary" @click="removeViolation">Удалить</b-button>
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

  </div>
</template>

<script>

import MainLayout from "@/layouts/MainLayout.vue";
import {url} from "@/main";
export default {
  name: 'ScheduleView',
  components: {MainLayout},
  data(){
    return {
      id1 : null,
      id2 : null,
      id3 : null,
      number : null,
      buses : null,
      violations : '',
      busId : null,
      violation : '',
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
      ],
      routes : [],

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
      }).then(() => this.getData())
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
      this.createData()
      this.showAlert("Добавлено!")

    },
    updateRoute() {
      this.updateData()
      this.showAlert("Обновлено!")
    },
    deleteRoute() {
      this.deleteData()
      this.showAlert("Удалено!")
    },
    addBus() {
      if (this.busId != null) {
        if (this.id2 != null) {
          this.$http.get(url + "/bus/" + this.busId.toString())
              .then(response => {
                const bus = response && response.data ? response.data : []
                //localStorage.setItem('drivers', JSON.stringify(drivers))
                this.$http.patch(url + "/route/addBusTo/" + this.id2.toString(),
                    bus
                ).catch((e) => {
                  console.log(e.toString())
                  this.alertText = "Ошибка!"
                }).then(() => this.getData())
              }).catch(e => {
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
        if (this.id2 != null) {
          this.$http.get(url + "/bus/" + this.busId.toString())
              .then(response => {
                const bus = response && response.data ? response.data : []
                //localStorage.setItem('drivers', JSON.stringify(drivers))
                this.$http.patch(url + "/route/removeBusFrom/" + this.id2.toString(),
                    bus
                ).catch((e) => {
                  console.log(e.toString())
                  this.alertText = "Ошибка!"
                }).then(() => this.getData())
              }).catch(e => {
            console.log(e)
          })
        } else {
          this.showAlert("Введите номер маршрута!")
        }
      } else {
        this.showAlert("Введите номер автобуса!")
      }
    },
    //TODO Сделать красивое отображение нарушений и зависимых комнонентов
    //TODO Сделать кнопки как ячейку у каждой строчки
    //TODO Добавить реализацию методов для нарушений
    addViolation() {
      if (this.id3 != null) {
        this.$http.patch(url + "/route/addViolationTo/" + this.id3.toString(),
            {
              violation: this.violation,
            }
        ).catch((e) => {
          console.log(e.toString())
          this.alertText = "Ошибка!"
        }).then(() => this.getData())
        this.showAlert("Добавлено!")
      } else {
        this.showAlert("Введите номер!")
      }
    },

    removeViolation() {
      if (this.id3 != null) {
        this.$http.patch(url + "/route/removeViolationsFrom/" + this.id3.toString()
        ).catch((e) => {
          console.log(e.toString())
          this.alertText = "Ошибка!"
        }).then(() => this.getData())
        this.showAlert("Удалено!")
      } else {
        this.showAlert("Введите номер!")
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
  background-image: url('\\img\\route2.jpg');
  height: 100vh;
}

</style>