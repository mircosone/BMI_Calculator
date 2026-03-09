package BMI_Master_MircoElong;

public class BMIEnglish {
    public void english(Data user) {

        Report.welcomeMessage(user);
        Input.dataInputEnglish(user);
        Report.makeReport(user);
        Report.makeTable(user);
        Report.exitMessage(user);
    }
}
