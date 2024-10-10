package fouragrant.scentasy.biz.perfume.dto;

import fouragrant.scentasy.biz.perfume.domain.Accord;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FlaskResponse {
    private String predictedNotes;
    private List<Accord> predictedAccords;
}
