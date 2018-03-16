package wordnet.App.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chien on 28/02/2018.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Result {
    private String resultId;
    private List<String> words = new ArrayList<>(0);
}
