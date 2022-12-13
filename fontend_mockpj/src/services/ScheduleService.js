import axios from 'axios'

const SCHEDULE_API_LIST_SCHEDULE = 'http://localhost:8080/api/schedule/listdto'
const SCHEDULE_API_UPLOAD_FILE = 'http://localhost:8080/api/schedule/upload'
const SCHEDUE_ERROR_EXPORT_FILE = 'http://localhost:8080/api/schedule/export'
const SCHEDUE_API_LIST_SCHEDULE_ERROR = 'http://localhost:8080/api/schedule/scheduleerrors'

class ScheduleService{
    getSchedules(){
        return axios.get(SCHEDULE_API_LIST_SCHEDULE);
    }

    upload(fileData){
        let fromData = new FormData();
        fromData = fileData;
        return axios.post(SCHEDULE_API_UPLOAD_FILE,fromData,{
            headers:{
                "Content-Type": "multipart/form-data"
            }
        });
    }

    getFileScheduleErrorByExcel(){
        return axios.get(SCHEDUE_ERROR_EXPORT_FILE);
    }

    getScheduleErrors(){
        return axios.get(SCHEDUE_API_LIST_SCHEDULE_ERROR);
    }
}

export default new ScheduleService()