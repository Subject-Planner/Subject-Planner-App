package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.annotations.BelongsTo;


import java.util.List;
import java.util.UUID;
import java.util.Objects;

import androidx.core.util.ObjectsCompat;

import com.amplifyframework.core.model.AuthStrategy;
import com.amplifyframework.core.model.Model;
import com.amplifyframework.core.model.ModelOperation;
import com.amplifyframework.core.model.annotations.AuthRule;
import com.amplifyframework.core.model.annotations.Index;
import com.amplifyframework.core.model.annotations.ModelConfig;
import com.amplifyframework.core.model.annotations.ModelField;
import com.amplifyframework.core.model.query.predicate.QueryField;

import static com.amplifyframework.core.model.query.predicate.QueryField.field;

/** This is an auto generated class representing the Grade type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Grades",  authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "bySubject", fields = {"subjectId","term"})
public final class Grade implements Model {
  public static final QueryField ID = field("Grade", "id");
  public static final QueryField DATE = field("Grade", "date");
  public static final QueryField WEIGHT = field("Grade", "weight");
  public static final QueryField TERM = field("Grade", "term");
  public static final QueryField SUBJECT_OBJECT = field("Grade", "subjectId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime date;
  private final @ModelField(targetType="Int") Integer weight;
  private final @ModelField(targetType="String") String term;
  private final @ModelField(targetType="Subject") @BelongsTo(targetName = "subjectId",  type = Subject.class) Subject subjectObject;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime createdAt;
  private @ModelField(targetType="AWSDateTime", isReadOnly = true) Temporal.DateTime updatedAt;
  /** @deprecated This API is internal to Amplify and should not be used. */
  @Deprecated
   public String resolveIdentifier() {
    return id;
  }
  
  public String getId() {
      return id;
  }
  
  public Temporal.DateTime getDate() {
      return date;
  }
  
  public Integer getWeight() {
      return weight;
  }
  
  public String getTerm() {
      return term;
  }
  
  public Subject getSubjectObject() {
      return subjectObject;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Grade(String id, Temporal.DateTime date, Integer weight, String term, Subject subjectObject) {
    this.id = id;
    this.date = date;
    this.weight = weight;
    this.term = term;
    this.subjectObject = subjectObject;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Grade grade = (Grade) obj;
      return ObjectsCompat.equals(getId(), grade.getId()) &&
              ObjectsCompat.equals(getDate(), grade.getDate()) &&
              ObjectsCompat.equals(getWeight(), grade.getWeight()) &&
              ObjectsCompat.equals(getTerm(), grade.getTerm()) &&
              ObjectsCompat.equals(getSubjectObject(), grade.getSubjectObject()) &&
              ObjectsCompat.equals(getCreatedAt(), grade.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), grade.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getDate())
      .append(getWeight())
      .append(getTerm())
      .append(getSubjectObject())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Grade {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("date=" + String.valueOf(getDate()) + ", ")
      .append("weight=" + String.valueOf(getWeight()) + ", ")
      .append("term=" + String.valueOf(getTerm()) + ", ")
      .append("subjectObject=" + String.valueOf(getSubjectObject()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static BuildStep builder() {
      return new Builder();
  }
  
  /**
   * WARNING: This method should not be used to build an instance of this object for a CREATE mutation.
   * This is a convenience method to return an instance of the object with only its ID populated
   * to be used in the context of a parameter in a delete mutation or referencing a foreign key
   * in a relationship.
   * @param id the id of the existing item this instance will represent
   * @return an instance of this model with only ID populated
   */
  public static Grade justId(String id) {
    return new Grade(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      date,
      weight,
      term,
      subjectObject);
  }
  public interface BuildStep {
    Grade build();
    BuildStep id(String id);
    BuildStep date(Temporal.DateTime date);
    BuildStep weight(Integer weight);
    BuildStep term(String term);
    BuildStep subjectObject(Subject subjectObject);
  }
  

  public static class Builder implements BuildStep {
    private String id;
    private Temporal.DateTime date;
    private Integer weight;
    private String term;
    private Subject subjectObject;
    public Builder() {
      
    }
    
    private Builder(String id, Temporal.DateTime date, Integer weight, String term, Subject subjectObject) {
      this.id = id;
      this.date = date;
      this.weight = weight;
      this.term = term;
      this.subjectObject = subjectObject;
    }
    
    @Override
     public Grade build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Grade(
          id,
          date,
          weight,
          term,
          subjectObject);
    }
    
    @Override
     public BuildStep date(Temporal.DateTime date) {
        this.date = date;
        return this;
    }
    
    @Override
     public BuildStep weight(Integer weight) {
        this.weight = weight;
        return this;
    }
    
    @Override
     public BuildStep term(String term) {
        this.term = term;
        return this;
    }
    
    @Override
     public BuildStep subjectObject(Subject subjectObject) {
        this.subjectObject = subjectObject;
        return this;
    }
    
    /**
     * @param id id
     * @return Current Builder instance, for fluent method chaining
     */
    public BuildStep id(String id) {
        this.id = id;
        return this;
    }
  }
  

  public final class CopyOfBuilder extends Builder {
    private CopyOfBuilder(String id, Temporal.DateTime date, Integer weight, String term, Subject subjectObject) {
      super(id, date, weight, term, subjectObject);
      
    }
    
    @Override
     public CopyOfBuilder date(Temporal.DateTime date) {
      return (CopyOfBuilder) super.date(date);
    }
    
    @Override
     public CopyOfBuilder weight(Integer weight) {
      return (CopyOfBuilder) super.weight(weight);
    }
    
    @Override
     public CopyOfBuilder term(String term) {
      return (CopyOfBuilder) super.term(term);
    }
    
    @Override
     public CopyOfBuilder subjectObject(Subject subjectObject) {
      return (CopyOfBuilder) super.subjectObject(subjectObject);
    }
  }
  


  
}
