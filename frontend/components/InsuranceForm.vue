<template>
  <div class="container">
    <Card>
      <template #title>Calculate Insurance Premium</template>

      <template #content>
        <div class="form-container">
          <div class="p-fluid p-formgrid grid">
            <!-- Vehicle type -->
            <div class="field col-12 md:col-4">
              <label for="vehicleType">Vehicle Type</label>
              <Dropdown
                  id="vehicleType"
                  v-model="form.vehicleType"
                  :options="vehicleTypes"
                  option-label="label"
                  option-value="value"
                  placeholder="Select a vehicle"
              />
            </div>

            <!-- Annual mileage -->
            <div class="field col-12 md:col-4">
              <label for="km">Annual Mileage</label>
              <InputNumber
                  id="km"
                  v-model="form.annualMileage"
                  :min="0"
                  :step="1000"
                  placeholder="e.g. 12000"
                  showButtons
              />
            </div>

            <!-- Postal code -->
            <div class="field col-12 md:col-4">
              <label for="plz">Postal Code</label>
              <InputText
                  id="plz"
                  v-model="form.postalCode"
                  placeholder="e.g. 50667"
              />
            </div>

            <!-- Submit -->
            <div class="field col-12">
              <Button
                  :disabled="!isValid || isLoading"
                  icon="pi pi-calculator"
                  label="Calculate"
                  @click="submit"
              />
            </div>

            <!-- Result -->
            <div v-if="result?.calculatedPremium != null" class="mt-3">
              <Card class="mt-3">
                <template #title>Result</template>
                <template #content>
                  <p><strong>Premium:</strong> {{ result.calculatedPremium.toFixed(2) }}</p>
                  <p><strong>ID:</strong> {{ result.id }}</p>
                </template>
              </Card>
            </div>
          </div>
        </div>
      </template>
    </Card>
  </div>
</template>

<script lang="ts" setup>
import {computed, ref} from 'vue'
import type {InsuranceRequestDTO} from '~/types/InsuranceRequest'
import {useInsuranceStore} from '~/stores/insurance'
import {useVehicleStore} from '~/stores/vehicle'

const store = useInsuranceStore()
const vehicleStore = useVehicleStore()

// Form state
const form = ref<InsuranceRequestDTO>({
  postalCode: '',
  vehicleType: '',
  annualMileage: 0,
})

onMounted(async () => {
  await vehicleStore.loadFactors()
})

// Validation
const isValid = computed(() =>
    form.value.postalCode.trim().length >= 4 &&
    form.value.vehicleType.length > 0 &&
    form.value.annualMileage > 0
)

const submit = async () => {
  await store.calculate(form.value)
}

// Result from store
const vehicleTypes = computed(() => vehicleStore.vehicleTypes)
const result = computed(() => store.result)
const isLoading = computed(() => store.isLoading)
</script>

<style scoped>

.form-title {
  text-align: center;
  margin: 0;
}

.form-container {
  margin-top: 1.5rem;
}
</style>
