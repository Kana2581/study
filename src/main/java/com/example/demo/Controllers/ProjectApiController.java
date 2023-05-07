package com.example.demo.Controllers;

import com.example.demo.entity.RestBean;
import com.example.demo.entity.SimpleAccountAndUserInformation;
import com.example.demo.service.ProjectService;
import com.example.demo.utils.CookieUtil;
import com.example.demo.utils.RandomNameUtil;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequestMapping("/project/api")
@RestController
public class ProjectApiController {
    @Resource
    ProjectService projectService;
    @PostMapping("/up-project")
    public RestBean upLoadProject(@RequestParam("cover") String cover, @RequestParam("title") String title, @RequestParam("profile") String profile,@RequestParam("status") int status, HttpServletRequest request)
    {
        String uid= CookieUtil.getCookieFromCookies(request.getCookies(),"uid");
        if(uid!=null)
        {
            projectService.addProject(Integer.parseInt(uid),cover,title,profile,status);
            return new RestBean(200,"hah");

        }else
            return new RestBean(302,"no cookie");

    }
    @GetMapping("/myProjects")
    public RestBean getMyProjects(HttpServletRequest request)
    {
        String uid;
        if(request.getParameter("uid")!=null&&!request.getParameter("uid").equals(""))
        {
            uid=request.getParameter("uid");
        }
        else
            uid= CookieUtil.getCookieFromCookies(request.getCookies(),"uid");
        if(uid!=null)
        {
            return new RestBean(200,"hah",projectService.getProjectsByUid(Integer.parseInt(uid)));

        }else
            return new RestBean(302,"no cookie");

    }
    @GetMapping("/my-course-history-list")
    public RestBean getMyProjectsHistory(HttpServletRequest request, Authentication authentication)
    {
        String username = authentication.getAuthorities().toString();
        String uid;
        if(request.getParameter("uid")!=null&&!request.getParameter("uid").equals(""))
        {
            uid=request.getParameter("uid");
        }
        else
        {
            uid= CookieUtil.getCookieFromCookies(request.getCookies(),"uid");
        }
        return new RestBean(200,"hah",projectService.getCoursesHistoryByUid(Integer.parseInt(uid)));

    }
    @GetMapping("/deleteProject")
    public void deleteProject(@RequestParam("pid") int pid, HttpServletRequest request, HttpServletResponse response)
    {
        String uid= CookieUtil.getCookieFromCookies(request.getCookies(),"uid");


            try {
                response.sendRedirect("/course-management?delete="+projectService.deleteProjectByPid(pid,uid));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

    }
    @GetMapping("/simple-account-user-information-list")
    public SimpleAccountAndUserInformation[] simpleProject()
    {
      return projectService.getSimpleAccountAndUserInformation();
    }
    @GetMapping("/project-page")
    public RestBean getProjects(@RequestParam(value = "search", required = false, defaultValue = "") String search,@RequestParam(value = "page", required = false, defaultValue = "1") String page)
    {
        return new RestBean(200,"hah",projectService.getProjectsByPage(Integer.parseInt(page),"%"+search+"%"));
    }
    @GetMapping("/project-information")
    public RestBean videoInformation(@RequestParam("pid") int pid)
    {
        return new RestBean(200,"hah",projectService.getProjectByPid(pid));
    }
    @GetMapping("/project-page-num")
    public RestBean projectPageNum()
    {
        return new RestBean(200,"hah",projectService.getProjectsNums());
    }
    @PostMapping("/edit-project")
    public RestBean editProject(@RequestParam("pid") int pid,@RequestParam("title") String title,@RequestParam("profile") String profile,@RequestParam("status") int status,@RequestParam(value = "cover", required = false,defaultValue = "") String cover,HttpServletRequest request)
    {
        String uid= CookieUtil.getCookieFromCookies(request.getCookies(),"uid");
        if(uid!=null) {
            if (cover.equals("")) {
                projectService.changeProject(title, profile,status, pid, uid);
            } else {
                projectService.changeProject(title, profile,status, cover, pid, uid);
            }
            return new RestBean(200, "hah");
        }else
            return new RestBean(302,"no cookie");

    }
}
