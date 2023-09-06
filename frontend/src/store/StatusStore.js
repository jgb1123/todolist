import axios from "../utils/axios.js";

export const useStatusStore = defineStore('statuses', () => {
    const statuses = ref([]);
    const statusNames = ref([]);

    const getStatuses = async () => {
        const res = await axios.get('/status/find')
        if(res.status === 200) {
            statuses.value = res.data.data;
            statusNames.value = res.data.data.map(s => s.statusName)
        }
    }

    return {statuses, statusNames, getStatuses}
})