package com.amplifyframework.datastore.generated.model;

import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.core.model.annotations.HasMany;
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

/** This is an auto generated class representing the Subject type in your schema. */
@SuppressWarnings("all")
@ModelConfig(pluralName = "Subjects", authRules = {
  @AuthRule(allow = AuthStrategy.PUBLIC, operations = { ModelOperation.CREATE, ModelOperation.UPDATE, ModelOperation.DELETE, ModelOperation.READ })
})
@Index(name = "byStudent", fields = {"studentId","title"})
public final class Subject implements Model {
  public static final QueryField ID = field("Subject", "id");
  public static final QueryField TITLE = field("Subject", "title");
  public static final QueryField START_DATE = field("Subject", "startDate");
  public static final QueryField END_DATE = field("Subject", "endDate");
  public static final QueryField IMAGES = field("Subject", "images");
  public static final QueryField NOTES = field("Subject", "notes");
  public static final QueryField NUMBER_OF_ABSENTS = field("Subject", "numberOfAbsents");
  public static final QueryField DAYS = field("Subject", "days");
  public static final QueryField STUDENT_PERSON = field("Subject", "studentId");
  private final @ModelField(targetType="ID", isRequired = true) String id;
  private final @ModelField(targetType="String", isRequired = true) String title;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime startDate;
  private final @ModelField(targetType="AWSDateTime") Temporal.DateTime endDate;
  private final @ModelField(targetType="String") List<String> images;
  private final @ModelField(targetType="String") List<String> notes;
  private final @ModelField(targetType="Grade") @HasMany(associatedWith = "subjectObject", type = Grade.class) List<Grade> grades = null;
  private final @ModelField(targetType="Int") Integer numberOfAbsents;
  private final @ModelField(targetType="DaysEnum") List<DaysEnum> days;
  private final @ModelField(targetType="Student") @BelongsTo(targetName = "studentId",  type = Student.class) Student studentPerson;
  private final @ModelField(targetType="Event") @HasMany(associatedWith = "subject", type = Event.class) List<Event> events = null;
  private final @ModelField(targetType="Record") @HasMany(associatedWith = "subject", type = Record.class) List<Record> records = null;
  private final @ModelField(targetType="File") @HasMany(associatedWith = "subject", type = File.class) List<File> files = null;
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
  
  public String getTitle() {
      return title;
  }
  
  public Temporal.DateTime getStartDate() {
      return startDate;
  }
  
  public Temporal.DateTime getEndDate() {
      return endDate;
  }
  
  public List<String> getImages() {
      return images;
  }
  
  public List<String> getNotes() {
      return notes;
  }
  
  public List<Grade> getGrades() {
      return grades;
  }
  
  public Integer getNumberOfAbsents() {
      return numberOfAbsents;
  }
  
  public List<DaysEnum> getDays() {
      return days;
  }
  
  public Student getStudentPerson() {
      return studentPerson;
  }
  
  public List<Event> getEvents() {
      return events;
  }
  
  public List<Record> getRecords() {
      return records;
  }
  
  public List<File> getFiles() {
      return files;
  }
  
  public Temporal.DateTime getCreatedAt() {
      return createdAt;
  }
  
  public Temporal.DateTime getUpdatedAt() {
      return updatedAt;
  }
  
  private Subject(String id, String title, Temporal.DateTime startDate, Temporal.DateTime endDate, List<String> images, List<String> notes, Integer numberOfAbsents, List<DaysEnum> days, Student studentPerson) {
    this.id = id;
    this.title = title;
    this.startDate = startDate;
    this.endDate = endDate;
    this.images = images;
    this.notes = notes;
    this.numberOfAbsents = numberOfAbsents;
    this.days = days;
    this.studentPerson = studentPerson;
  }
  
  @Override
   public boolean equals(Object obj) {
      if (this == obj) {
        return true;
      } else if(obj == null || getClass() != obj.getClass()) {
        return false;
      } else {
      Subject subject = (Subject) obj;
      return ObjectsCompat.equals(getId(), subject.getId()) &&
              ObjectsCompat.equals(getTitle(), subject.getTitle()) &&
              ObjectsCompat.equals(getStartDate(), subject.getStartDate()) &&
              ObjectsCompat.equals(getEndDate(), subject.getEndDate()) &&
              ObjectsCompat.equals(getImages(), subject.getImages()) &&
              ObjectsCompat.equals(getNotes(), subject.getNotes()) &&
              ObjectsCompat.equals(getNumberOfAbsents(), subject.getNumberOfAbsents()) &&
              ObjectsCompat.equals(getDays(), subject.getDays()) &&
              ObjectsCompat.equals(getStudentPerson(), subject.getStudentPerson()) &&
              ObjectsCompat.equals(getCreatedAt(), subject.getCreatedAt()) &&
              ObjectsCompat.equals(getUpdatedAt(), subject.getUpdatedAt());
      }
  }
  
  @Override
   public int hashCode() {
    return new StringBuilder()
      .append(getId())
      .append(getTitle())
      .append(getStartDate())
      .append(getEndDate())
      .append(getImages())
      .append(getNotes())
      .append(getNumberOfAbsents())
      .append(getDays())
      .append(getStudentPerson())
      .append(getCreatedAt())
      .append(getUpdatedAt())
      .toString()
      .hashCode();
  }
  
  @Override
   public String toString() {
    return new StringBuilder()
      .append("Subject {")
      .append("id=" + String.valueOf(getId()) + ", ")
      .append("title=" + String.valueOf(getTitle()) + ", ")
      .append("startDate=" + String.valueOf(getStartDate()) + ", ")
      .append("endDate=" + String.valueOf(getEndDate()) + ", ")
      .append("images=" + String.valueOf(getImages()) + ", ")
      .append("notes=" + String.valueOf(getNotes()) + ", ")
      .append("numberOfAbsents=" + String.valueOf(getNumberOfAbsents()) + ", ")
      .append("days=" + String.valueOf(getDays()) + ", ")
      .append("studentPerson=" + String.valueOf(getStudentPerson()) + ", ")
      .append("createdAt=" + String.valueOf(getCreatedAt()) + ", ")
      .append("updatedAt=" + String.valueOf(getUpdatedAt()))
      .append("}")
      .toString();
  }
  
  public static TitleStep builder() {
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
  public static Subject justId(String id) {
    return new Subject(
      id,
      null,
      null,
      null,
      null,
      null,
      null,
      null,
      null
    );
  }
  
  public CopyOfBuilder copyOfBuilder() {
    return new CopyOfBuilder(id,
      title,
      startDate,
      endDate,
      images,
      notes,
      numberOfAbsents,
      days,
      studentPerson);
  }
  public interface TitleStep {
    BuildStep title(String title);
  }
  

  public interface BuildStep {
    Subject build();
    BuildStep id(String id);
    BuildStep startDate(Temporal.DateTime startDate);
    BuildStep endDate(Temporal.DateTime endDate);
    BuildStep images(List<String> images);
    BuildStep notes(List<String> notes);
    BuildStep numberOfAbsents(Integer numberOfAbsents);
    BuildStep days(List<DaysEnum> days);
    BuildStep studentPerson(Student studentPerson);
  }
  

  public static class Builder implements TitleStep, BuildStep {
    private String id;
    private String title;
    private Temporal.DateTime startDate;
    private Temporal.DateTime endDate;
    private List<String> images;
    private List<String> notes;
    private Integer numberOfAbsents;
    private List<DaysEnum> days;
    private Student studentPerson;
    public Builder() {
      
    }
    
    private Builder(String id, String title, Temporal.DateTime startDate, Temporal.DateTime endDate, List<String> images, List<String> notes, Integer numberOfAbsents, List<DaysEnum> days, Student studentPerson) {
      this.id = id;
      this.title = title;
      this.startDate = startDate;
      this.endDate = endDate;
      this.images = images;
      this.notes = notes;
      this.numberOfAbsents = numberOfAbsents;
      this.days = days;
      this.studentPerson = studentPerson;
    }
    
    @Override
     public Subject build() {
        String id = this.id != null ? this.id : UUID.randomUUID().toString();
        
        return new Subject(
          id,
          title,
          startDate,
          endDate,
          images,
          notes,
          numberOfAbsents,
          days,
          studentPerson);
    }
    
    @Override
     public BuildStep title(String title) {
        Objects.requireNonNull(title);
        this.title = title;
        return this;
    }
    
    @Override
     public BuildStep startDate(Temporal.DateTime startDate) {
        this.startDate = startDate;
        return this;
    }
    
    @Override
     public BuildStep endDate(Temporal.DateTime endDate) {
        this.endDate = endDate;
        return this;
    }
    
    @Override
     public BuildStep images(List<String> images) {
        this.images = images;
        return this;
    }
    
    @Override
     public BuildStep notes(List<String> notes) {
        this.notes = notes;
        return this;
    }
    
    @Override
     public BuildStep numberOfAbsents(Integer numberOfAbsents) {
        this.numberOfAbsents = numberOfAbsents;
        return this;
    }
    
    @Override
     public BuildStep days(List<DaysEnum> days) {
        this.days = days;
        return this;
    }
    
    @Override
     public BuildStep studentPerson(Student studentPerson) {
        this.studentPerson = studentPerson;
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
    private CopyOfBuilder(String id, String title, Temporal.DateTime startDate, Temporal.DateTime endDate, List<String> images, List<String> notes, Integer numberOfAbsents, List<DaysEnum> days, Student studentPerson) {
      super(id, title, startDate, endDate, images, notes, numberOfAbsents, days, studentPerson);
      Objects.requireNonNull(title);
    }
    
    @Override
     public CopyOfBuilder title(String title) {
      return (CopyOfBuilder) super.title(title);
    }
    
    @Override
     public CopyOfBuilder startDate(Temporal.DateTime startDate) {
      return (CopyOfBuilder) super.startDate(startDate);
    }
    
    @Override
     public CopyOfBuilder endDate(Temporal.DateTime endDate) {
      return (CopyOfBuilder) super.endDate(endDate);
    }
    
    @Override
     public CopyOfBuilder images(List<String> images) {
      return (CopyOfBuilder) super.images(images);
    }
    
    @Override
     public CopyOfBuilder notes(List<String> notes) {
      return (CopyOfBuilder) super.notes(notes);
    }
    
    @Override
     public CopyOfBuilder numberOfAbsents(Integer numberOfAbsents) {
      return (CopyOfBuilder) super.numberOfAbsents(numberOfAbsents);
    }
    
    @Override
     public CopyOfBuilder days(List<DaysEnum> days) {
      return (CopyOfBuilder) super.days(days);
    }
    
    @Override
     public CopyOfBuilder studentPerson(Student studentPerson) {
      return (CopyOfBuilder) super.studentPerson(studentPerson);
    }
  }
  


  
}
