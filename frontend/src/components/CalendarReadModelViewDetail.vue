<template>

    <v-card outlined>
        <v-card-title>
            CalendarReadModel # {{$route.params.id }}
        </v-card-title>

        <v-card-text>
            <div>
                <String label="UserId" v-model="item.userId" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="YearAndMonth" v-model="item.yearAndMonth" :editMode="editMode" @change="change" />
            </div>
            <div>
                <String label="ScheduleData" v-model="item.scheduleData" :editMode="editMode" @change="change" />
            </div>
        </v-card-text>
    </v-card>

</template>

<script>
  const axios = require('axios').default;

  export default {
    name: 'CalendarReadModelViewDetail',
    props: {
      value: Object,
    },
    data: () => ({
        item : [],
    }),
    async created() {
      var params = this.$route.params;
      var temp = await axios.get(axios.fixUrl('/calendarReadModels/' + params.id))

      this.item = temp.data;

    },
    methods: {
    }
  }
</script>

