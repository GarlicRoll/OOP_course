
<template>
  <div>
    <main-layout></main-layout>
    <b-carousel
        id="carousel-1"
        v-model="slide"
        :interval="4000"
        controls
        indicators
        background="#ababab"
        img-width="1024"
        img-height="480"
        style="text-shadow: 1px 1px 2px #333;"
        @sliding-start="onSlideStart"
        @sliding-end="onSlideEnd"
    >
      <!-- Text slides with image -->
      <b-carousel-slide
          caption="Транспортная система"

          img-src="https://3park.ru/wp-content/uploads/2021/10/mobile_file_2021-10-15_11-54-03.jpg"
      ></b-carousel-slide>

      <!-- Slides with custom text -->
      <b-carousel-slide img-src="https://resizer.mail.ru/p/d86f3cb3-c654-5d0c-8bc8-fd0176e74c98/AQABY8tqJ1DCnqMdDOdt4pQaKtaplb5mjWGhXfjzZyvxmmw9IuUYLNAK-WlXpEUu_8h7wal8BrltTuhK8R709PC-Hdk.jpg"
                        caption="Транспортная система"
                        text="Добро пожаловать в наш инновационный сервис.">

      </b-carousel-slide>

      <!-- Slides with image only -->
      <b-carousel-slide img-src="https://arbuztoday.ru/wp-content/uploads/2023/01/20230112_115345.jpg"></b-carousel-slide>

      <!-- Slides with img slot -->
      <!-- Note the classes .d-block and .img-fluid to prevent browser default image alignment -->
      <b-carousel-slide img-src="https://pitertransport.com/assets/images/uploaded/1641063262979.jpg"></b-carousel-slide>

    </b-carousel>
  </div>
</template>


<script>
import MainLayout from "@/layouts/MainLayout.vue";

export default {
  name: 'ScheduleView',
  components: {MainLayout},
  data() {
    return {
      slide: 0,
      sliding: null
    }
  },
  methods: {
    onSlideStart() {
      this.sliding = true
    },
    onSlideEnd() {
      this.sliding = false
    }
  }
}

</script>