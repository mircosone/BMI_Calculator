package BMI_Master_MircoElong;

public class BMIMetric {
    public void metric(Data user) {

        Report.welcomeMessage(user);
        Input.dataInputMetric(user);
        Report.makeReport(user);
        Report.makeTable(user);
        Report.exitMessage(user);
    }
}
