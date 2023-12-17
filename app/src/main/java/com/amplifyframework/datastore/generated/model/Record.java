package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.annotations.BelongsTo;
import com.amplifyframework.core.model.temporal.Temporal;


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

/** This is an auto generated class representing the Record type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Records",  authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "bySubject", fields = {"subjectId","name"})
public final class Record implements Model {
  public static final QueryField ID = field("Record", "id");
  public static final QueryField NAME = field("Record", "name");
  public static final QueryField LINK = field("Record", "link");
  public static final QueryField SUBJECT = field("Record", "subjectId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="String", isRequired = true) String link;
  private final @ModelField(targetType="Subject") @BelongsTo(targetName = "subjectId", type = Subject.class) Subject subject;
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
  
  public String getName() {
      return name;
  }
  
  public String getLink() {
      return link;
  }
  
  public Subject getSubject() {
      return subject;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Record(String id, String name, String link, Subject subject) {
    this.id = id;
    this.name = name;
    this.link = link;
    this.subject = subject;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Record record = (Record) obj;
      return ObjectsCompat.equals(getId(), record.getId()) &&
              ObjectsCompat.equals(getName(), record.getName()) &&
              ObjectsCompat.equals(getLink(), record.getLink()) &&
              ObjectsCompat.equals(getSubject(), record.getSubject()) &&
              ObjectsCompat.equals(getCreatedAt(), record.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), record.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getLink())
      .append(getSubject())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Record {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("link=" + String.valueOf(getLink()) + ", ")
      .append("subject=" + String.valueOf(getSubject()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static NameStep builder() {
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
  public static Record justId(String id) {
    return new Record(
      id,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      link,
      subject);
  }
  public interface NameStep {
    LinkStep name(String name);
  }
  

  public interface LinkStep {
    BuildStep link(String link);
  }
  

  public interface BuildStep {
    Record build();
    BuildStep id(String id);
    BuildStep subject(Subject subject);
  }
  

  public static class Builder implements NameStep, LinkStep, BuildStep {
    private String id;
    private String name;
    private String link;
    private Subject subject;
    public Builder() {
      
    }
    
    private Builder(String id, String name, String link, Subject subject) {
      this.id = id;
      this.name = name;
      this.link = link;
      this.subject = subject;
    }
    
    @Override
     public Record build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Record(
          id,
          name,
          link,
          subject);
    }
    
    @Override
     public LinkStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep link(String link) {
        Objects.requireNonNull(link);
        this.link = link;
        return this;
    }
    
    @Override
     public BuildStep subject(Subject subject) {
        this.subject = subject;
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
    private CopyOfBuilder(String id, String name, String link, Subject subject) {
      super(id, name, link, subject);
      Objects.requireNonNull(name);
      Objects.requireNonNull(link);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder link(String link) {
      return (CopyOfBuilder) super.link(link);
    }
    
    @Override
     public CopyOfBuilder subject(Subject subject) {
      return (CopyOfBuilder) super.subject(subject);
    }
  }
  


}
