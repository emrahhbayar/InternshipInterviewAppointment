package app.repository;

import app.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment,Integer>
{
    List<Appointment> findByDateBetween(Date start,Date end);
    List<Appointment> findByStudentId(int studentId);
    List<Appointment> findByTeacherIdAndTakenOrderByDate(int teacherId,boolean taken);
}