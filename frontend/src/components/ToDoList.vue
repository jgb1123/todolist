<template>
  <div class="q-pa-md">
    <q-select
        filled
        v-model="selectModel"
        use-input
        use-chips
        multiple
        input-debounce="0"
        :options="statusStore.$state.statusNames"
        style="width: 500px"
    />
    <q-table
      class="my-sticky-header-table"
      flat bordered
      :rows="toDoItems"
      :columns="columns"
      row-key="name"
      :hide-pagination="true"
      :pagination.sync="pagination"
    >
      <template v-slot:body-cell-actions="props">
        <q-td :props="props">
          <q-btn icon="mode_edit" color="light-blue-4" @click="editItem(props.row)" />
          <q-btn icon="delete" color="light-blue-4" @click="confirm(props.row.itemId)" />
        </q-td>
      </template>
    </q-table>
  </div>
</template>



<script setup>
import axios from '../utils/axios.js';
import dayjs from "dayjs";
import {useItemStore} from "../store/ItemStore.js";
import {useStatusStore} from "../store/StatusStore.js";

const router = useRouter();
const $q = useQuasar();
const itemStore = useItemStore();
const statusStore = useStatusStore();
// const page = ref(1);
const emit = defineEmits(['change-edit-pop-up', 'refresh-todo-list'])

const pagination = {
  page: 1,
  rowsPerPage: 0
}
const selectModel = ref([]);

const toDoItems = computed(() => {
  if(selectModel.value.length > 0) {
    return itemStore.$state.items.filter((i) => selectModel.value.some((s) => s === i.statusName))
  }
  return itemStore.$state.items;
})

const columns = [
  {
    name: 'date',
    required: true,
    label: 'Date',
    align: 'left',
    field: 'targetTime',
    format: val => `${dayjs(val).format('YYYY-MM-DD HH:mm')}`,
    sortable: true
  },
  {
    name: 'title',
    required: true,
    label: 'title',
    align: 'left',
    field: 'title',
    format: val => `${val}`,
    sortable: false
  },
  {
    name: 'content',
    required: true,
    label: 'content',
    align: 'left',
    field: 'content',
    format: val => `${val}`,
    sortable: false
  },
  {
    name: 'status',
    required: true,
    label: 'status',
    align: 'left',
    field: 'statusName',
    format: val => `${val}`,
    sortable: true
  },
  {
    name: 'actions',
    align: 'center',
    label: 'Action'
  }
]

const confirm = (itemId) => {
  console.log(selectModel.value)
  $q.dialog({
    ok: {
      color: 'negative'
    },
    cancel: true,
    message: '해당 일정을 정말 삭제하시겠습니까?',
  }).onOk(() => {
    deleteItem(itemId)
  })
}

const alertDelete = () => {
  $q.dialog({
    message: '삭제되었습니다.'
  })
}

const deleteItem = async (itemId) => {
  const res = await axios.post(`/item/delete/${itemId}`)
  if(res.status === 204){
    alertDelete()
    emit('refresh-todo-list')
  }
}

const editItem = async (item) => {
  emit(`change-edit-pop-up`, item)
}

</script>

<style lang="sass">
.my-sticky-header-table
  .q-table__top,
  .q-table__bottom,
  thead tr:first-child th
    background-color: #B8EBFF

  thead tr th
    position: sticky
    z-index: 1
  thead tr:first-child th
    top: 0

  &.q-table--loading thead tr:last-child th
    top: 10px

  tbody
    scroll-margin-top: 48px
</style>