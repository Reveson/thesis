package siemieniuk.thesis.searchservice.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@Document(indexName = "user")
public class User {
    @Id
    private String id;

    @Field(type = FieldType.Text)
    private String login;

    @Field(type = FieldType.Text)
    private String name;

    @Field(type = FieldType.Text)
    private String surname;
}
