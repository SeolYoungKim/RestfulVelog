<script setup lang="ts">

import {defineProps, onMounted, ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";

const props = defineProps({
  postId: {
    type: [Number, String],
    require: true,
  }
});

const post = ref({
  id: 0,
  title: "",
  text: "",
});

const router = useRouter();

const moveToEdit = () => {
  router.push({ name: "edit", params: {postId: props.postId} })
}

onMounted(() => {
  axios.get(`/api/article/${props.postId}`).then((response) => {
    console.log(response);
    post.value = response.data;
  })
})

</script>

<template>
  <h2>{{post.title}}</h2>
  <div>{{post.text}}</div>

  <el-button type="warning" @click="moveToEdit()">수정</el-button>
</template>