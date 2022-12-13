<template>
<div class="container-fluid">
  <h1>Schedule List</h1>

<form ref = "uploadFrom" @submit.prevent="submit">
    <label class="btn btn-default">
    <input type="file" ref="uploadFile" @change="onFileUpload()" class="form-control" required/>
    </label>
    <button type="submit" @click="startUpload" class="btn btn-success"  >Submit</button>
</form>  

<div>
    <a v-if="isShow" v-bind:href="'http://localhost:8080/api/schedule/export'"><button class="btn btn-warning" @click="exportFile" >Export File Schedue Error</button> </a>
</div>

<div>
    <a v-if="isShow" >Link</a>
</div>
  <table class="table table-striped table-hover">
    <thead>
          <th scope="col" >Schedule Id</th>
          <th>Account Id</th>
          <th>Campaign Id</th>
          <!-- <th>File Id</th> -->
          <th>Campaign Status</th>
          <th>Delivery Change From</th>
          <th>Delivery Change To</th>
          <th>Done</th>
          <!-- <th>Action</th> -->
      </thead>
      <tbody>
          <tr v-for="schedule in schedules" v-bind:key="schedule.id">
          <td>{{schedule.id}}</td>
          <td>{{schedule.account_id}}</td>
          <td>{{schedule.campaign_id}}</td>
          <!-- <td>{{schedule.file.file_id}}</td> -->
          <td>{{schedule.campaign_status}}</td>
          <td>{{schedule.delivery_change_from}}</td>
          <td>{{schedule.delivery_change_to}}</td>
          <td><input type="checkbox"  v-model="schedule.action_done" /></td>

        </tr>
      </tbody>
  </table>
</div>
</template>

<script>
import ScheduleService from '../services/ScheduleService'
    export default  {
        name: "schedule",
        data(){
            return {
                schedules : [], // schedule list
                formData : null, // file
                isShow: false

            }
             
        },
        methods:{
            getSchedules(){
                ScheduleService.getSchedules().then((response) =>{
                    this.schedules = response.data;
                })
            }
            ,
            onFileUpload(){
                let fileUP = this.$refs.uploadFile.files[0];
                    this.formData = new FormData();
                    this.formData.append("file",fileUP);
                
            },

            startUpload(){
                ScheduleService.upload(this.formData).then( res =>{
                        this.say();
                        this.isShow = true;
                        this.formData = null;
                        // this.$refs.uploadFile.files[0] = null;
                        return this.getSchedules();
                })
            },

             say() {
             alert("Success")
             },

             exportFile(){
                this.isShow = false;
             }

       
        },
        created(){
            this.getSchedules()
        }

    }
</script>
