<template>
  <div>
    <main-layout></main-layout>
    <b-button v-b-toggle.sidebar-border>Toggle Sidebar</b-button>
    <b-sidebar id="sidebar-border" sidebar-class="border-right border-danger">
      <div class="px-3 py-2">
        <p>
          Cras mattis consectetur purus sit amet fermentum. Cras justo odio, dapibus ac facilisis
          in, egestas eget quam. Mosrbi leo risus, porta ac consectetur ac, vestibulum at eros.
        </p>
        <b-img src="https://picsum.photos/500/500/?image=54" fluid thumbnail></b-img>
      </div>
    </b-sidebar>
  </div>
</template>

<script>
import MainLayout from "@/layouts/MainLayout.vue";
export default {
  name: 'HomeView',
  components: {MainLayout}
}
</script>


