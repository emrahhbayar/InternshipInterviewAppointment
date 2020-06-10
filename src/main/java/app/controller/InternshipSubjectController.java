package app.controller;

import app.entity.InternshipSubject;
import app.entity.InternshipType;
import app.service.InternshipSubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/internshipSubject")
@CrossOrigin("*")
public class InternshipSubjectController
{
    @Autowired
    private InternshipSubjectService internshipSubjectService;
    @PostMapping
    public InternshipSubject save(@RequestBody InternshipSubject internshipSubject)
    {
        internshipSubjectService.save(internshipSubject);
        return internshipSubject;
    }
    @PutMapping
    public void update(@RequestBody InternshipSubject internshipSubject)
    {
        internshipSubjectService.update(internshipSubject);
    }
    @GetMapping("/{id}")
    public InternshipSubject findById(@PathVariable int id)
    {
        return internshipSubjectService.findById(id);
    }
    @GetMapping
    public List<InternshipSubject> findAll()
    {
        return internshipSubjectService.findAll();
    }
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable int id)
    {
        internshipSubjectService.deleteById(id);
    }
    @GetMapping("/type/{name}")
    public List<InternshipSubject> findByType(@PathVariable String name)
    {
        System.out.println(name);
        if(name.equals("1"))
            return internshipSubjectService.findByType(InternshipType.YAZILIM);
        else if(name.equals("0"))
            return internshipSubjectService.findByType(InternshipType.DONANIM);
        return List.of();
    }
}