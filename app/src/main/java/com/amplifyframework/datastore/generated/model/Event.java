package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.annotations.BelongsTo;


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

/** This is an auto generated class representing the Event type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Events",  authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "bySubject", fields = {"subjectId","date"})
public final class Event implements Model {
  public static final QueryField ID = field("Event", "id");
  public static final QueryField NAME = field("Event", "name");
  public static final QueryField DATE = field("Event", "date");
  public static final QueryField TIME = field("Event", "time");
  public static final QueryField SUBJECT = field("Event", "subjectId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String name;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime date;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime time;
  private final @ModelField(targetType="Subject") @BelongsTo(targetName = "subjectId",  type = Subject.class) Subject subject;
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
  
  public Temporal.DateTime getDate() {
      return date;
  }
  
  public Temporal.DateTime getTime() {
      return time;
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
  
  public Event(String id, String name, Temporal.DateTime date, Temporal.DateTime time, Subject subject) {
    this.id = id;
    this.name = name;
    this.date = date;
    this.time = time;
    this.subject = subject;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Event event = (Event) obj;
      return ObjectsCompat.equals(getId(), event.getId()) &&
              ObjectsCompat.equals(getName(), event.getName()) &&
              ObjectsCompat.equals(getDate(), event.getDate()) &&
              ObjectsCompat.equals(getTime(), event.getTime()) &&
              ObjectsCompat.equals(getSubject(), event.getSubject()) &&
              ObjectsCompat.equals(getCreatedAt(), event.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), event.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getName())
      .append(getDate())
      .append(getTime())
      .append(getSubject())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Event {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("name=" + String.valueOf(getName()) + ", ")
      .append("date=" + String.valueOf(getDate()) + ", ")
      .append("time=" + String.valueOf(getTime()) + ", ")
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
  public static Event justId(String id) {
    return new Event(
      id,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      name,
      date,
      time,
      subject);
  }
  public interface NameStep {
    BuildStep name(String name);
  }
  

  public interface BuildStep {
    Event build();
    BuildStep id(String id);
    BuildStep date(Temporal.DateTime date);
    BuildStep time(Temporal.DateTime time);
    BuildStep subject(Subject subject);
  }
  

  public static class Builder implements NameStep, BuildStep {
    private String id;
    private String name;
    private Temporal.DateTime date;
    private Temporal.DateTime time;
    private Subject subject;
    public Builder() {
      
    }
    
    private Builder(String id, String name, Temporal.DateTime date, Temporal.DateTime time, Subject subject) {
      this.id = id;
      this.name = name;
      this.date = date;
      this.time = time;
      this.subject = subject;
    }
    
    @Override
     public Event build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Event(
          id,
          name,
          date,
          time,
          subject);
    }
    
    @Override
     public BuildStep name(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        return this;
    }
    
    @Override
     public BuildStep date(Temporal.DateTime date) {
        this.date = date;
        return this;
    }
    
    @Override
     public BuildStep time(Temporal.DateTime time) {
        this.time = time;
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
    private CopyOfBuilder(String id, String name, Temporal.DateTime date, Temporal.DateTime time, Subject subject) {
      super(id, name, date, time, subject);
      Objects.requireNonNull(name);
    }
    
    @Override
     public CopyOfBuilder name(String name) {
      return (CopyOfBuilder) super.name(name);
    }
    
    @Override
     public CopyOfBuilder date(Temporal.DateTime date) {
      return (CopyOfBuilder) super.date(date);
    }
    
    @Override
     public CopyOfBuilder time(Temporal.DateTime time) {
      return (CopyOfBuilder) super.time(time);
    }
    
    @Override
     public CopyOfBuilder subject(Subject subject) {
      return (CopyOfBuilder) super.subject(subject);
    }
  }
  


  
}
