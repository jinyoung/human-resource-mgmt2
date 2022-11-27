<template>

    <v-card outlined>
        <v-card-title>
            VacationStatus # {{$route.params.id }}
        </v-card-title>

        <v-card-text>
            <div>
                <Date label="StartDate" v-model="item.startDate" :editMode="editMode" @change="change" />
            </div>
            <div>
                <Date label="EndDate" v-model="item.endDate" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="Reason" v-model="item.reason" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="Status" v-model="item.status" :editMode="editMode" @change="change" />
            </div>
        </v-card-text>
    </v-card>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'VacationStatusViewDetail',
    props: {
      value: Object,
    },
    data: () => ({
        item : [],
    }),
    async created() {
      var params = this.$route.params;
      var temp = await axios.get(axios.fixUrl('/vacationStatuses/' + params.id))

      this.item = temp.data;

    },
    methods: {
    }
  }
</script>

