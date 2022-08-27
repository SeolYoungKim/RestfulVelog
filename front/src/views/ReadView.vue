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

  <el-row>
    <el-col>
      <h2 class="title">{{post.title}}</h2>

      <div class="sub">
          <span class="category">개발</span>
          <span class="regDate">2022-08-26</span>
      </div>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <div class="content">{{post.text}}</div>
    </el-col>
  </el-row>

  <el-row class="mt-3">
    <el-col>
      <div class="d-flex justify-content-end">
        <el-button type="warning" @click="moveToEdit()">수정</el-button>
      </div>
    </el-col>
  </el-row>
</template>

<style scoped lang="scss">
.title {
  font-size: 1.6rem;
  font-weight: 600;
  color: #383838;
  margin: 0;
}

.content {
  font-size: 0.95rem;
  margin-top: 8px;
  color: #616161;
  white-space: break-spaces;
  line-height: 1.5;
}

.sub {
  margin-top: 10px;
  font-size: 0.78rem;

  .regDate {
    margin-left: 10px;
    color: #6b6b6b;
  }
}
</style>