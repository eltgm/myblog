package ru.sultanyarov.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

@Document("users")
public class User {
    @Id
    private ObjectId id;
    private String name;
    private String password;
    @Field("github_key")
    private String githubKey;
    @Field("gitlab_key")
    private String gitlabKey;
    @Field("full_name")
    private String fullName;
}
