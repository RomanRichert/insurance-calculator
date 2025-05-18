<template>
  <div class="container">
    <Card>
      <template #title>History of Requests</template>

      <template #content>
        <DataTable
            v-if="entries.length"
            :rows="10"
            :rowsPerPageOptions="[5, 10, 20]"
            :value="entries"
            class="mt-3"
            paginator
            responsiveLayout="scroll"
            stripedRows
        >
          <Column field="id" header="ID"/>
          <Column field="vehicleType" header="Vehicle"/>
          <Column field="annualMileage" header="Mileage"/>
          <Column field="postalCode" header="Postal Code"/>
          <Column field="calculatedPremium" header="Premium">
            <template #body="{ data }">
              {{ data.calculatedPremium.toFixed(2) }}
            </template>
          </Column>
          <Column field="createdAt" header="Created At">
            <template #body="{ data }">
              {{ new Date(data.createdAt).toLocaleString() }}
            </template>
          </Column>
        </DataTable>

        <p v-else class="mt-2">No records available.</p>
      </template>
    </Card>
  </div>
</template>

<script lang="ts" setup>
import {computed, onMounted} from 'vue'
import {useInsuranceStore} from '~/stores/insurance'

const store = useInsuranceStore()
const entries = computed(() => store.entries)

onMounted(async () => {
  await store.loadAll()
})
</script>

<style scoped>
.mt-3 {
  margin-top: 1.5rem;
}

</style>
