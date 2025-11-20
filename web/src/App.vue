<template>
  <div>
    <NavBar></NavBar>
    <RouterView />
  </div>
</template>

<script>
import NavBar from './components/NavBar.vue';
import "bootstrap/dist/css/bootstrap.min.css";
import "bootstrap/dist/js/bootstrap.bundle.min.js";
// import $ from 'jquery';

export default {
  components: {
    NavBar
  },
  setup() {
    // $.ajax({
    //   url: "http://127.0.0.1:3000/user/account/token/",
    //   type: "post",
    //   contentType: "application/json", // 这里必须加上，不然403
    //   data: JSON.stringify({ // JSON.stringify这里必须加上，不然403
    //     username: "admin",
    //     password: "123"
    //   }),
    //   success(resp) {
    //     console.log(resp);
    //   },
    //   error(resp) {
    //     console.error(resp);
    //   }
    // });

    // $.ajax({
    //   url: "http://127.0.0.1:3000/user/account/info/",
    //   type: "get",
    //   contentType: "application/json", // 这里必须加上，不然403
    //   headers: {
    //     Authorization: "Bearer " + "eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI4ZDk1ZDE5MTZmNWE0OGM1OTRiMTA5NzI1MGUwNDIzMSIsInN1YiI6IjEiLCJpc3MiOiJzZyIsImlhdCI6MTc2MjM1MTM2NSwiZXhwIjoxNzYzNTYwOTY1fQ.D6MALu76K7KoDKFASQp3uhkNFkG_a4j1nE2WjtEs8Gc",
    //   },
    //   success(resp) {
    //     console.log(resp);
    //   },
    //   error(resp) {
    //     console.error(resp);
    //   }
    // });

    // $.ajax({
    //   url: "http://127.0.0.1:3000/user/account/register/",
    //   type: "post",
    //   data: {
    //     username: "c",
    //     password: "123",
    //     confirmedPassword: "123"
    //   },
    //   success(resp) {
    //     console.log(resp);
    //   },
    //   error(resp) {
    //     console.error(resp);
    //   }
    // })
  }
}
</script>

<style>
body {
  background-image: url("@/assets/images/background.png");
  background-size: cover;
}
</style>
