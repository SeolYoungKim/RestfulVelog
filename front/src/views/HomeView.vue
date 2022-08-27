<script setup lang="ts">
// const posts = [
//   {id: 1, title: "제목1", content: "내용1"},
//   {id: 2, title: "제목2", content: "내용2"},
//   {id: 3, title: "제목3", content: "내용3"},
//   {id: 4, title: "제목4", content: "내용4"},
//   {id: 5, title: "제목5", content: "내용5"},
// ];

import axios from "axios";
import {ref} from "vue";
import {useRouter} from "vue-router";

const router = useRouter();

const posts = ref([]);

axios.get("/api/articles?page=1&&size=5").then(response => {
      console.log(response)
      response.data.forEach((r: any) => {
        posts.value.push(r);
      });
    });

</script>

<template>
<!--  <div v-for="post in posts" :key="post.id"></div>-->
  <ul>
    <li v-for="post in posts" :key="post.id" >
      <div class="title">
        <!-- /read 는 프론트의 url 이다. 서버로의 요청은 axios 가 담당함.-->
        <!-- read라는 이름으로 라우터에 등록된 url로 이동하며, params를 통해 postId라는 이름으로 변수를 전달할 수 있다.-->
        <router-link :to="{ name: 'read', params: {postId: post.id} }">{{ post.title }}</router-link>
        <!--a 태그는 전체를 다시불러오기 때문에 리소스 낭비가 심하다.-->
      </div>

      <div class="content">
        {{ post.text }}
      </div>

      <div class="sub d-flex">
        <span class="category">개발</span>
        <span class="regDate">2022-08-26</span>
      </div>

    </li>
  </ul>
</template>

<style scoped lang="scss">
ul {
  list-style: none;
  padding: 0;

  li {
    margin-bottom: 2rem;

    .title {
      a {
        font-size: 1.1rem;
        color: #383838;
        text-decoration: none;
      }

      &:hover {
        text-decoration: underline;
      }
    }

    .content {
      font-size: 0.85rem;
      margin-top: 8px;
      color: #7e7e7e;
    }

    &:last-child {
      margin-bottom: 0;
    }

    .sub {
      margin-top: 8px;
      font-size: 0.78rem;

      .regDate {
        margin-left: 10px;
        color: #6b6b6b;
      }
    }
  }
}


</style>
