<template>
  <InsuranceForm/>
  <InsuranceHistory/>
  <Toast/>
</template>

<script lang="ts" setup>
import InsuranceHistory from '~/components/InsuranceHistory.vue'
import InsuranceForm from '~/components/InsuranceForm.vue'
import Toast from "primevue/toast";
</script>

<style>
.container {
  max-width: 900px;
  margin: 2rem auto;
  padding: 0 1rem;
}

</style>