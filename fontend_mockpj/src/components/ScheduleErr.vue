<template>
    <div class="container-fluid">
        <h1>Schedule Error List</h1>
      
        <table class="table table-striped table-hover">
          <thead>
                <th scope="col" >Schedule Error Id</th>
                <th>Account Id</th>
                <th>Campaign Id</th>
                <th>Campaign Status</th>
                <th>Delivery Change From</th>
                <th>Delivery Change To</th>
                <th>File Id</th>
                <th>Description</th>
                <!-- <th>Action</th> -->
            </thead>
            <tbody>
                <tr v-for="scheduleErr in schedulesErrs" v-bind:key="scheduleErr.id">
                <td>{{scheduleErr.id}}</td>
                <td>{{scheduleErr.account_id}}</td>
                <td>{{scheduleErr.campaign_id}}</td>
                <!-- <td>{{schedule.file.file_id}}</td> -->
                <td>{{scheduleErr.campaign_status}}</td>
                <td>{{scheduleErr.delivery_change_from}}</td>
                <td>{{scheduleErr.delivery_change_to}}</td>
                <td>{{scheduleErr.file_id}}</td>
                <td>
                    <button>Detail</button>
                </td>
      
              </tr>
            </tbody>
        </table>
      </div>
</template>

import ScheduleService from '../services/ScheduleService'

<script>
import ScheduleService from '@/services/ScheduleService';
// import { response } from 'express';

export default {
    name: "scheduleErr",
    data: {
        schedulesErrs: [],
        check: false
    },
    methods: {
        getScheduleErrs(){
            ScheduleService.getScheduleErrors().then((response) => {
                this.schedulesErrs = response.data;
            })
        }    
    }

}
</script>