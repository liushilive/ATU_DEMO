package liushilive.github.io.ui.home;

import java.io.Serializable;

public class UserModel implements Serializable {
    public String name;
    public String birthdate;
    public int years_of_working;
    public int self_score;
    public String time;

    public UserModel() {
        this.name = "请输入姓名：";
        this.birthdate = "";
        this.years_of_working = 0;
        this.self_score = 0;
        this.time = "";
    }
}
