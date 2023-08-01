package com.example.finalproject_;


import java.util.ArrayList;
import java.util.List;

public class DataSpiner {
    public static List<TypeMeeting> getTypeMeetingList() {
        List<TypeMeeting> typeMeetingList = new ArrayList<>();


        TypeMeeting other = new TypeMeeting();
        other.setName("  סוג הפגישה ");
        other.setImage(R.drawable.nothing);
        typeMeetingList.add(other);

        TypeMeeting doctor = new TypeMeeting();
        doctor.setName("  רופא ");
        doctor.setImage(R.drawable.doctor);
        typeMeetingList.add(doctor);

        TypeMeeting occupational_therapy = new TypeMeeting();
        occupational_therapy.setName("  ריפוי בעיסוק ");
        occupational_therapy.setImage(R.drawable.physiotherapist);
        typeMeetingList.add(occupational_therapy);

        TypeMeeting emotional_therapy = new TypeMeeting();
        emotional_therapy.setName("  טיפול רגשי ");
        emotional_therapy.setImage(R.drawable.emo);
        typeMeetingList.add(emotional_therapy);

        TypeMeeting speech_therapist = new TypeMeeting();
        speech_therapist.setName("  קלינאי תקשורת ");
        speech_therapist.setImage(R.drawable.speech_therapist);
        typeMeetingList.add(speech_therapist);

        return typeMeetingList;
    }
}