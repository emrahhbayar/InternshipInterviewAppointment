package app.controller;

import app.entity.Appointment;
import app.entity.Teacher;
import app.service.AppointmentService;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.time.temporal.ChronoUnit.HOURS;

@RestController
@RequestMapping("/appointment")
@CrossOrigin("*")
public class AppointmentController
{
    @Autowired
    private AppointmentService appointmentService;
    @PostMapping
    public Appointment save(@RequestBody Appointment appointment)
    {
        appointmentService.save(appointment);
        return appointment;
    }
    @GetMapping("/day/{date}")
    public List<Appointment> findByDay(@PathVariable @DateTimeFormat(pattern="yyyy-MM-dd") Date date)
    {
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY,23);
        calendar.add(Calendar.MINUTE,59);
        Date end=calendar.getTime();
        return appointmentService.findByDateBetween(date,end);
    }
    @PutMapping
    public void update(@RequestBody Appointment appointment)
    {
        appointmentService.update(appointment);
    }
    @GetMapping("/{id}")
    public Appointment findById(@PathVariable int id)
    {
        return appointmentService.findById(id);
    }
    @GetMapping
    public List<Appointment> findAll()
    {
        return appointmentService.findAll();
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id)
    {
        appointmentService.deleteById(id);
    }
    @PostMapping("/multiple")
    public void save(@RequestBody MultipleAppointment multipleAppointment, Authentication authentication)
    {
        Teacher teacher=(Teacher)authentication.getPrincipal();
        long diffInMillies = Math.abs(multipleAppointment.start.getTime() - multipleAppointment.end.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        long minutes=(HOURS.between(multipleAppointment.startTime,multipleAppointment.endTime))*60/multipleAppointment.minute;
        for (int i = 0; i < diff+1; i++)
        {
            for (int j = 0; j < minutes+1; j++) {
                Appointment appointment=new Appointment();
                Date date=new Date(multipleAppointment.start.getTime()+86400000*i);
                Calendar calendar=Calendar.getInstance();
                calendar.setTime(date);
                calendar.set(Calendar.HOUR,multipleAppointment.startTime.getHour()+(j/(60/multipleAppointment.minute)));
                calendar.set(Calendar.MINUTE,multipleAppointment.minute*(j%(60/multipleAppointment.minute)));
                appointment.setTeacher(teacher);
                appointment.setDate(calendar.getTime());
                appointmentService.save(appointment);
            }
        }
    }
    @GetMapping("/teacher/{teacherId}")
    public List<Appointment> findByTeacherIdAndTakenOrderByDate(@PathVariable int teacherId)
    {
        return appointmentService.findByTeacherIdAndTakenOrderByDate(teacherId);
    }
    @GetMapping("/student/{id}")
    public List<Appointment> findByStudentId(@PathVariable int id)
    {
        return appointmentService.findByStudentId(id);
    }
    @Getter
    @Setter
    private static class MultipleAppointment
    {
        private Date start,end;
        private LocalTime startTime,endTime;
        private int minute;

        @Override
        public String toString() {
            return start+" "+end+" "+startTime+" "+endTime+" "+minute;
        }
    }
}