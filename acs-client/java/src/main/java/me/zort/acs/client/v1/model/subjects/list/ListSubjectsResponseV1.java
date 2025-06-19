package me.zort.acs.client.v1.model.subjects.list;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import me.zort.acs.client.AcsSubjectResolvable;
import me.zort.acs.client.v1.model.SubjectV1;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class ListSubjectsResponseV1 {
    private List<SubjectV1> subjects;

    public List<AcsSubjectResolvable> getSubjects() {
        return subjects
                .stream()
                .map(subject -> (AcsSubjectResolvable) subject)
                .toList();
    }
}
