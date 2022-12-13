package mockpj.upload.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class UpdateCampaignController {

    //@Autowired
//    ScheduleService scheduleService;
//
//        @Scheduled(fixedRate = 600000)
//    public ResponseEntity<String> updateCampainBySchedule(){

//        GoogleAdsClient googleAdsClient = null;
//
//
//        try(CampaignServiceClient campaignServiceClient =
//                    googleAdsClient.getLatestVersion().createCampaignServiceClient()) {
//
//
//            List<Schedule> scheduleList = scheduleService.get100ScheduleToAction();
//            for(Schedule schedule: scheduleList) {
//
//                CampaignStatusEnum.CampaignStatus campaignStatus;
//                if (schedule.getCampaign_status().equals("Active")) {
//                    campaignStatus = CampaignStatusEnum.CampaignStatus.ENABLED;
//                } else if (schedule.getCampaign_status().equals("Remove")) {
//                    campaignStatus = CampaignStatusEnum.CampaignStatus.REMOVED;
//                } else if (schedule.getCampaign_status().equals("Pause")) {
//                    campaignStatus = CampaignStatusEnum.CampaignStatus.PAUSED;
//                } else campaignStatus = CampaignStatusEnum.CampaignStatus.UNKNOWN;
//
//                // setup campaign to action
//                Campaign campaign = Campaign.newBuilder().
//                        setResourceName(ResourceNames.campaign(schedule.getAccount().getAccount_id(), schedule.getCampaign().getCampaign_id())).
//                        setStatus(campaignStatus).
//                        build();
//
//                // FieldMasks list all field we want to action
//                CampaignOperation campaignOperation = CampaignOperation.newBuilder().
//                        setUpdate(campaign).
//                        setUpdateMask(FieldMasks.allSetFieldsOf(campaign)).
//                        build();
//
//                // Sends the operation in request.
//                MutateCampaignsResponse campaignsResponse = campaignServiceClient.
//                        mutateCampaigns(schedule.getAccount().getAccount_id().toString(), Collections.singletonList(campaignOperation));
//
//                // Prints the resource name of each updated object.
//                for (MutateCampaignResult mutateCampaignResult : campaignsResponse.getResultsList()) {
//                    System.out.printf(
//                            "Updated campaign with resourceName: %s.%n", mutateCampaignResult.getResourceName());
//                }
//
//
//            }
//        }
//
//        return null;
//    }
}
