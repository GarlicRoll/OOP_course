<template>
      <b-navbar toggleable="lg" type="dark" variant="info" v-bind:sticky="true">
        <b-navbar-brand href="/">Транспортная система</b-navbar-brand>

        <b-navbar-toggle target="nav-collapse"></b-navbar-toggle>

        <b-collapse id="nav-collapse" is-nav>
          <b-navbar-nav>
            <b-nav-item href="/drivers">Водители</b-nav-item>
            <b-nav-item href="/buses">Автобусы</b-nav-item>
            <b-nav-item href="/schedule">График</b-nav-item>
          </b-navbar-nav>

          <!-- Right aligned nav items -->
          <b-navbar-nav class="ml-auto">



            <b-nav-item-dropdown right>
              <!-- Using 'button-content' slot -->
              <template #button-content>
                <em>Пользовтель</em>
              </template>
              <b-dropdown-item href="#">Профиль</b-dropdown-item>
              <b-dropdown-item href="#">Выйти из аккаунта</b-dropdown-item>
            </b-nav-item-dropdown>
          </b-navbar-nav>
        </b-collapse>
      </b-navbar>
</template>

<script>
export default {
  name: 'ScheduleView',
}

</script>


<style scoped>

</style>