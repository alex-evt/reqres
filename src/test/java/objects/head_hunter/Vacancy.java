package objects.head_hunter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class Vacancy {
    @Expose
    String name;
    @Expose
    Salary salary;
    @Expose
    @SerializedName("alternate_url")
    String alternateUrl;
}
