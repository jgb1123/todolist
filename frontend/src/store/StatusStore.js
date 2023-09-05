export const useStatusStore = defineStore('statuses', () => {
    const statuses = ref([]);
    const statusNames = ref([]);

    const setStatuses = (inputStatuses) => {
        statuses.value = inputStatuses
    }
    const setStatusNames = (inputStatusNames) => {
        statusNames.value = inputStatusNames
    }

    return {statuses, statusNames, setStatuses, setStatusNames}
})