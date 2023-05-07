
function handleSubmit() {
    // 获取输入框的内容
    var inputText = document.getElementById("search-text").value;

    // 根据输入框内容来选择跳转页面
    if (inputText) {
        window.location.href = "/index?search="+inputText;
    }

    // 防止表单提交的默认行为
    return false;
}
function getUserInformation()
{
    $.get("/user/api/simpleUserInformation",function (data)
        {
            document.getElementById("user-name").innerText=data.data.name;
            document.getElementById("user-img").src="static/portraits/"+data.data.portrait;
        }
    )

}
function getQueryString(name) {
    let reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)", "i");
    let r = window.location.search.substr(1).match(reg);
    if (r != null) return unescape(r[2]); return null;
}
function submitUserInformation()
{

    let address=document.getElementById('form-address');
    $.post("/user/api/submitUserInformation",{
        name:document.getElementById("form-name").value,
        phone:document.getElementById("form-phone").value,
        birthday:document.getElementById("form-birthday").value,
        address: address.options[address.selectedIndex].value,
        profile: document.getElementById("additional-msg").value,
        uid:getQueryString("uid")
        
        },
        function (data)
        {
            location.reload();
        }

    )
}
function setUserProfile()
{
    let uid=getQueryString("uid");
    let url="/user/api/userInformation";
    let url1="/auth/api/account-information"
    if(uid)
    {
        url=url+"?uid="+uid;
        url1=url1+"?uid="+uid;
    }

    $.get(url,function (data)
        {
            document.getElementById("form-name").innerText=data.data.name;
            document.getElementById("form-img").src="static/portraits/"+data.data.portrait;
            document.getElementById("form-phone").innerText="手机号是"+data.data.phone;
            document.getElementById("form-birthday").innerText="生日是"+data.data.birthday;
            document.getElementById("additional-msg").innerText=data.data.profile;
            document.getElementById("form-address").innerText="地址是"+data.data.address;
        }
    )
    $.get(url1,function (data)
        {

            document.getElementById("account-time").innerText="加入时间是"+data.data.createTime.substring(0,10);
            document.getElementById("account-role").innerText=data.data.role==="user"?"student":"teacher";
            let url;
            if(data.data.role==='user')
            {
                document.getElementById("course-title").innerText="学习的课程";
                url="/project/api/my-course-history-list"

            }else
                document.getElementById("course-title").innerText="发布的课程";
                url="/project/api/myProjects";
            getSpaceProjects(url);

        }
    )
}
function getUserAllInformation()
{
    $.get("/user/api/userInformation",{uid:getQueryString("uid")},function (data)
        {
            document.getElementById("form-name").value=data.data.name;
            document.getElementById("form-phone").value=data.data.phone;
            document.getElementById("form-birthday").value=data.data.birthday;
            document.getElementById("additional-msg").value=data.data.profile;
            document.getElementById("finalImg").src="static/portraits/"+data.data.portrait;
            let sel = document.getElementById('form-address');
            let val = data.data.address;
            for(let i = 0, j = sel.options.length; i < j; ++i) {
                if(sel.options[i].value== val) {
                    sel.selectedIndex = i;
                    break;
                }
            }

        }
    )

}
function getJsonLength(json){
    let jsonLength=0;
    for (let i in json) {
        jsonLength++;
    }
    return jsonLength;
}
function getCourseInformation()
{
    let url="/project/api/project-information?pid="+getQueryString("pid");
    $.get(url,function (data) {
        document.getElementById("course-cover").src="static/covers/"+data.data.cover;
        document.getElementById("course-title").innerText=data.data.title;
        document.getElementById("course-profile").innerText=document.getElementById("detail-profile").innerText=data.data.profile;
        document.getElementById("course-up").innerText="上传者:"+data.data.name;
        document.getElementById("course-video-list").href="/watch?pid="+getQueryString("pid");
    });
    getVideoComments(getQueryString("pid"),"projectComments");
}
function getCourseByPid()
{
    let url="/project/api/project-information?pid="+getQueryString("pid");
    $.get(url,function (data) {
        document.getElementById("finalImg").src="static/covers/"+data.data.cover;
        document.getElementById("project-title").value=data.data.title;
        document.getElementById("project-message").value=data.data.profile;
        document.getElementById("project-status").selectedIndex=parseInt(data.data.status);

    });
}

function getProjects()
{

    let projectArea=document.getElementById("project-area");
    let projectDiv;
    let url="/project/api/project-page";



    $.get(url,{page:getQueryString("page"),search:getQueryString("search")},function (data)
        {

            for (let i=0;i<data.data.length;++i) {
                if(i%4===0)
                {
                    projectDiv=document.createElement("div");
                    projectDiv.className="row";
                    projectArea.appendChild(projectDiv);
                }
                let div1=document.createElement("div");
                div1.className=" col-lg-3 col-md-3 col-sm-12 col-xs-12 mb-30 ";
                let div2=document.createElement("div");
                div2.className="media";
                div1.appendChild(div2);
                let a1=document.createElement("a");
                a1.href="/course?pid="+data.data[i].pid;
                let img=document.createElement("img");
                img.src="/static/covers/"+data.data[i].cover;
                img.className="projectImg";
                let div3=document.createElement("div");
                div3.className=" ms-3 col-sm-10 col-lg-10 col-xl-10 col-md-10 col-xs-10";
                div2.appendChild(a1);
                a1.appendChild(img);


                let img2=document.createElement("img");
                img2.src="/static/portraits/"+data.data[i].portrait;
                img2.className="media-portrait col-sm-2 col-lg-2 col-xl-2 col-md-2 col-xs-2";



                let div8=document.createElement("div");


                div2.appendChild(div8);
                div8.appendChild(img2);
                div8.appendChild(div3);

                let div4=document.createElement("div");
                div4.className="media-title";
                div4.innerText=data.data[i].title;
                let div5=document.createElement("div");
                div5.className="media-up";
                div5.innerText=data.data[i].name;
                let a2=document.createElement("a");
                a2.href="/space?uid="+data.data[i].uid;
                a2.appendChild(div5);
                let div6=document.createElement("div");
                div6.className="media-extra-message";
                let date=new Date(Date.now()-new Date(data.data[i].time));

                let project_time;
                console.log(date.getUTCDate()+" "+date.getUTCMonth());
                if(date.getUTCDate()===1&&date.getUTCMonth()===0) {
                    if (date.getUTCHours() === 0) {
                        if (date.getMinutes() === 0) {
                            project_time = date.getUTCSeconds() + "秒前";
                        } else {
                            project_time = date.getUTCMinutes() + "分钟前";
                        }

                    } else {
                        project_time = date.getUTCHours() + "小时前";
                    }

                }else
                {
                    project_time=data.data[i].time.substring(0,10);
                }


                div6.innerText="播放量"+data.data[i].amount+" , "+project_time;
                div3.appendChild(div4);
                div3.appendChild(a2);
                div3.appendChild(div6);
                projectDiv.appendChild(div1);

            }


        }
    )
}


function getSpaceProjects(url)
{

    let projectArea=document.getElementById("project-area");
    let projectDiv;

    $.get(url,{uid:getQueryString("uid")},function (data)
        {

            for (let i=0;i<data.data.length;++i) {
                if(i%3===0)
                {
                    projectDiv=document.createElement("div");
                    projectDiv.className="row";
                    projectArea.appendChild(projectDiv);
                }
                let div = document.createElement('div');
                div.className = 'col-xl-4 col-lg-4 col-md-4 mb-30';

// create the inner media element
                let media = document.createElement('div');
                media.className = 'media';
                div.appendChild(media);

// create the image element

                let img = document.createElement('img');
                img.className = 'videoImg';
                img.src = '/static/covers/'+data.data[i].cover;

                let a=document.createElement('a');
                a.href="course?pid="+data.data[i].pid;
                a.appendChild(img);

                media.appendChild(a);

// create the media body element
                let mediaBody = document.createElement('div');
                mediaBody.className = 'media-body ms-3';
                media.appendChild(mediaBody);

// create the media title element
                let mediaTitle = document.createElement('div');
                mediaTitle.className = 'media-title';
                mediaTitle.textContent = data.data[i].title;
                mediaBody.appendChild(mediaTitle);

// create the media up element
                let mediaUp = document.createElement('div');
                mediaUp.className = 'media-up';
                mediaUp.textContent =  data.data[i].profile;
                mediaBody.appendChild(mediaUp);

// create the media extra message element
                let mediaExtraMessage = document.createElement('div');
                mediaExtraMessage.className = 'media-extra-message';
                mediaExtraMessage.textContent = timeStampTransform(data.data[i].time) ;
                mediaBody.appendChild(mediaExtraMessage);

// add the created element to the DOM
                projectDiv.appendChild(div);

            }


        }
    )
}


function getCoursesHistory()
{

    let projectArea=document.getElementById("project-area");

    let url="/project/api/my-course-history-list";

    $.get(url,{uid:getQueryString("uid")},function (data)
        {

            for (let i=0;i<data.data.length;++i) {
                const outerDiv = document.createElement('div');
                outerDiv.className = 'media';
                outerDiv.style.borderBottom = '1px solid #e5e9ef';

// Create the anchor tag with href "/course?pid=1"
                const anchorTag = document.createElement('a');
                anchorTag.href = '/video-progress?pid='+data.data[i].pid;
                if(getQueryString("uid"))
                    anchorTag.href+="&uid="+getQueryString("uid") ;

// Create the image tag with src "static/covers/1683270230153@7517.jpeg" and class "projectImg col-sm-3 col-sm-offset-1 col-xs-12"
                const imageTag = document.createElement('img');
                imageTag.src = 'static/covers/'+data.data[i].cover;
                imageTag.className = 'projectImg col-sm-3 col-sm-offset-1 col-xs-12';

// Append the image tag to the anchor tag
                anchorTag.appendChild(imageTag);

// Create the inner div with class "ms-3 col-sm-6 col-xs-12"
                const innerDiv = document.createElement('div');
                innerDiv.className = 'ms-3 col-sm-6 col-xs-12';

// Create the media title div with class "media-title" and text "C语言程序设计50例"
                const mediaTitleDiv = document.createElement('div');
                mediaTitleDiv.className = 'media-title';
                mediaTitleDiv.textContent = data.data[i].title;

// Create the anchor tag with href "/space?uid=28"
                const innerAnchorTag = document.createElement('a');
                innerAnchorTag.href = '/space?uid='+data.data[i].uid;

// Create the media up div with class "media-up" and text "张辉454545"
                const mediaUpDiv = document.createElement('div');
                mediaUpDiv.className = 'media-up';
                mediaUpDiv.textContent ="上传者:"+data.data[i].name;

// Append the media up div to the inner anchor tag
                innerAnchorTag.appendChild(mediaUpDiv);

// Create the media extra message div with class "media-extra-message" and text "2023-02-21"
                const mediaExtraMessageDiv1 = document.createElement('div');
                mediaExtraMessageDiv1.className = 'media-extra-message';
                mediaExtraMessageDiv1.textContent ="最后一次学习时间:"+timestampToChineseTime(data.data[i].time);

// Create the media extra message div with class "media-extra-message" and text "久しぶりま"
                const mediaExtraMessageDiv2 = document.createElement('div');
                mediaExtraMessageDiv2.className = 'media-extra-message';
                mediaExtraMessageDiv2.textContent = data.data[i].profile;

// Append the media title div, inner anchor tag, media extra message divs to the inner div
                innerDiv.appendChild(mediaTitleDiv);
                innerDiv.appendChild(innerAnchorTag);
                innerDiv.appendChild(mediaExtraMessageDiv1);
                innerDiv.appendChild(mediaExtraMessageDiv2);

// Append the anchor tag and inner div to the outer div
                outerDiv.appendChild(anchorTag);
                outerDiv.appendChild(innerDiv);

// Append the outer div to the HTML body
                projectArea.appendChild(outerDiv);

            }


        }
    )
}


function getIndexHistory()
{

    let projectArea=document.getElementById('parent-element-id');

    let url="/project/api/my-course-history-list";

    $.get(url,function (data)
        {

            for (let i=0;i<data.data.length;++i) {
                let a=document.createElement('a');
                a.href="/course?pid="+data.data[i].pid;
                let container = document.createElement('div');
                container.classList.add('of-nt-flex');

// Create the div with class "of-nt-img" and its child img element
                let imgDiv = document.createElement('div');
                imgDiv.classList.add('of-nt-img');
                let img = document.createElement('img');
                img.setAttribute('src', 'static/covers/'+data.data[i].cover);
                imgDiv.appendChild(img);

// Create the div with class "of-nt-cmnt" and its child elements
                let cmntDiv = document.createElement('div');
                cmntDiv.classList.add('of-nt-cmnt');
                let p = document.createElement('p');
                p.innerText = data.data[i].title;
                cmntDiv.appendChild(p);

// Create the div with class "int-table-quantity" and its child elements
                let quantityDiv = document.createElement('div');
                quantityDiv.classList.add('int-table-quantity');
                let quantityWrapperDiv = document.createElement('div');
                quantityWrapperDiv.classList.add('quantity-wrapper');
                let quantityQtyDiv = document.createElement('div');
                quantityQtyDiv.classList.add('quantity-qty');
                let h5 = document.createElement('h5');
                h5.style.whiteSpace="nowrap";
                h5.innerText = data.data[i].name;
                let span = document.createElement('span');
                span.innerText = timestampToChineseTime(data.data[i].time);
                quantityQtyDiv.appendChild(h5);
                quantityQtyDiv.appendChild(span);
                quantityWrapperDiv.appendChild(quantityQtyDiv);
                quantityDiv.appendChild(quantityWrapperDiv);
                cmntDiv.appendChild(quantityDiv);

// Add the img and cmnt divs to the container div
                container.appendChild(a);
                a.appendChild(imgDiv);
                container.appendChild(cmntDiv);

// Append the container div to an existing parent element in your HTML file
                projectArea.appendChild(container);

            }


        }
    )
}


function getMyProjects()
{
    let projectArea=document.getElementById("project-list");
    let url="/project/api/myProjects";
    $.get(url,function (data)
        {
            for (let i=0;i<data.data.length;++i) {

                var row = document.createElement("tr");
                projectArea.appendChild(row);
// Create and append the table data elements to the row
                var id = document.createElement("td");
                id.innerText = "#"+data.data[i].pid;
                row.appendChild(id);

                var name = document.createElement("td");
                name.className="col-sm-2 col-xs-2";
                name.innerHTML = "<div class='profile'>"+data.data[i].title+"</div>" ;
                row.appendChild(name);

                var profile = document.createElement("td");
                profile.className="col-sm-2 col-xs-2";
                profile.innerHTML = "<div class='profile'>"+data.data[i].profile+"</div>";
                row.appendChild(profile);

                var date = document.createElement("td");
                date.innerText = data.data[i].time.substring(2,10);
                row.appendChild(date);

                var amount = document.createElement("td");
                amount.innerText = data.data[i].amount;
                row.appendChild(amount);

                var status = document.createElement("td");
                var badge = document.createElement("label");

                if(data.data[i].status==0)
                {
                    badge.classList.add("mb-0", "badge", "badge-success");
                    badge.innerText = "all people";
                }else if(data.data[i].status==1)
                {
                    badge.classList.add("mb-0", "badge", "badge-primary");
                    badge.innerText = "just student";
                }else
                {
                    badge.classList.add("mb-0", "badge", "badge-danger");
                    badge.innerText = "deny";
                }

                status.appendChild(badge);
                row.appendChild(status);



                const td = document.createElement("td");
                td.className = "relative";
                row.appendChild(td);
                const a = document.createElement("a");
                a.className = "action-btn";
                a.href = "javascript:void(0);";

                const svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
                svg.classList.add("default-size");
                svg.setAttribute("viewBox", "0 0 341.333 341.333");

                const g1 = document.createElementNS("http://www.w3.org/2000/svg", "g");
                const g2 = document.createElementNS("http://www.w3.org/2000/svg", "g");
                const g3 = document.createElementNS("http://www.w3.org/2000/svg", "g");


                const path1 = document.createElementNS("http://www.w3.org/2000/svg", "path");
                path1.setAttribute("d", "M170.667,85.333c23.573,0,42.667-19.093,42.667-42.667C213.333,19.093,194.24,0,170.667,0S128,19.093,128,42.667 C128,66.24,147.093,85.333,170.667,85.333z ");

                const path2 = document.createElementNS("http://www.w3.org/2000/svg", "path");
                path2.setAttribute("d", "M170.667,128C147.093,128,128,147.093,128,170.667s19.093,42.667,42.667,42.667s42.667-19.093,42.667-42.667 S194.24,128,170.667,128z ");

                const path3 = document.createElementNS("http://www.w3.org/2000/svg", "path");
                path3.setAttribute("d", "M170.667,256C147.093,256,128,275.093,128,298.667c0,23.573,19.093,42.667,42.667,42.667s42.667-19.093,42.667-42.667 C213.333,275.093,194.24,256,170.667,256z ");

                g3.appendChild(path1);
                g3.appendChild(path2);
                g3.appendChild(path3);
                g2.appendChild(g3);
                g1.appendChild(g2);
                svg.appendChild(g1);
                a.appendChild(svg);
                td.appendChild(a);

                const div = document.createElement("div");
                div.className = "action-option";

                const ul = document.createElement("ul");

                const li0 = document.createElement("li");
                const a0 = document.createElement("a");
                a0.href = "/course?pid="+data.data[i].pid;
                const i0 = document.createElement("i");
                i0.className = "far fa-file-alt mr-2";
                const text0 = document.createTextNode(" View");
                a0.appendChild(i0);
                a0.appendChild(text0);
                li0.appendChild(a0);
                ul.appendChild(li0);

                const li1 = document.createElement("li");
                const a1 = document.createElement("a");
                a1.href = "/project-modification?pid="+data.data[i].pid;
                const i1 = document.createElement("i");
                i1.className = "far fa-edit mr-2";
                const text1 = document.createTextNode("Edit");
                a1.appendChild(i1);
                a1.appendChild(text1);
                li1.appendChild(a1);
                ul.appendChild(li1);

                const li2 = document.createElement("li");
                const a2 = document.createElement("a");
                a2.href = "/project/api/deleteProject?pid="+data.data[i].pid;
                const i2 = document.createElement("i");
                i2.className = "far fa-trash-alt mr-2";
                const text2 = document.createTextNode("Delete");
                a2.appendChild(i2);
                a2.appendChild(text2);
                li2.appendChild(a2);
                ul.appendChild(li2);

                div.appendChild(ul);
                td.appendChild(div);


            }


        }
    )
}
function getMyVideoProgress()
{
    let projectArea=document.getElementById("project-list");
    let url="/video/api/my-video-progress";
    let p1=0,p2=0,p3=0;
    $.get(url,{pid:getQueryString("pid"),uid:getQueryString("uid")},function (data)
        {
            for (let i=0;i<data.data.length;++i) {

                var row = document.createElement("tr");
                projectArea.appendChild(row);
// Create and append the table data elements to the row


                var name = document.createElement("td");
                name.className="col-sm-4 col-xs-4";
                name.innerHTML = "<div class='profile'>"+data.data[i].title+"</div>" ;
                row.appendChild(name);

                var profile = document.createElement("td");
                profile.className="col-sm-2 col-xs-2";
                profile.innerHTML = data.data[i].duration;
                row.appendChild(profile);

                var status = document.createElement("td");
                var badge = document.createElement("label");

                if(data.data[i].studyTime==0)
                {
                    badge.classList.add("mb-0", "badge", "badge-danger");
                    badge.innerText = "还未学习";
                    p3++;

                }else if(parseInt(data.data[i].studyTime)<parseFloat(data.data[i].duration))
                {
                    badge.classList.add("mb-0", "badge", "badge-primary");
                    badge.innerText = "还未学完";
                    p2++;
                }else if(parseInt(data.data[i].studyTime)>=parseFloat(data.data[i].duration))
                {
                    badge.classList.add("mb-0", "badge", "badge-success");
                    badge.innerText = "已完成";
                    p1++;
                }


                status.appendChild(badge);
                row.appendChild(status);

                var action = document.createElement("td");
                var a1=document.createElement('a');
                a1.innerText="观看";
                action.appendChild(a1);
                a1.className="badge badge-primary";
                a1.href = "/watch?pid="+data.data[i].pid+"&v="+data.data[i].vid;


                row.appendChild(action);



            }
            createRadialChart(data.data.length,p1,p2,p3);

        }
    )
}
function createRadialChart (length,l1,l2,l3)
{
    const options = {
        colors: ['#99CC33','#0099CC','#FF0000'],
        chart: {
            height: 350,
            type: 'radialBar',
            offsetY: -20
        },
        series: [l1===0?0:l1/length*100, l2===0?0:l2/length*100, l3===0?0:l3/length*100],
        labels: ['已经完成', '还未看完', '还未看'],
        plotOptions: {
            radialBar: {
                dataLabels: {
                    name: {
                        fontSize: '22px',
                    },
                    value: {
                        fontSize: '16px',
                    },
                    total: {
                        show: true,
                        label: 'Total',
                        formatter: function (w) {
                            return length;
                        }
                    }
                }
            }
        },
        fill: {
            type: 'gradient',
            gradient: {
                shade: 'dark',
                shadeIntensity: 0.15,
                inverseColors: false,
                opacityFrom: 1,
                opacityTo: 1,
                stops: [0, 50, 65, 91]
            },
        },
        stroke: {
            lineCap: 'round'
        },
        labels: ['已经完成', '还未看完', '还未看'],
    }

    const chart = new ApexCharts(document.querySelector('#chart'), options);
    chart.render();
}
function getMyPdfs()
{

    let projectArea=document.getElementById("pdf-list");

    let url="/pdf/api/pdf-page?pid="+getQueryString("pid");

    $.get(url,function (data)
        {

            for (let i=0;i<data.data.length;++i) {

                var row = document.createElement("tr");
                projectArea.appendChild(row);
// Create and append the table data elements to the row
                var id = document.createElement("td");
                id.innerText = "#"+data.data[i].cwid;
                row.appendChild(id);

                var title = document.createElement("td");
                title.className="col-sm-2 col-xs-2";
                title.innerHTML = "<span class='profile'>"+data.data[i].title+"</span>" ;
                row.appendChild(title);

                var name = document.createElement("td");
                name.className="col-sm-2 col-xs-2";
                name.innerHTML = "<a class='profile' href='/static/pdfs/"+data.data[i].pdf+"'>"+data.data[i].pdf+"</a>" ;
                row.appendChild(name);



                var date = document.createElement("td");
                date.className="nowrap";
                date.innerText = data.data[i].time.substring(0,10);
                row.appendChild(date);


                const td = document.createElement("td");
                td.className = "relative";
                row.appendChild(td);
                const a = document.createElement("a");
                a.className = "action-btn";
                a.href = "javascript:void(0);";

                const svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
                svg.classList.add("default-size");
                svg.setAttribute("viewBox", "0 0 341.333 341.333");

                const g1 = document.createElementNS("http://www.w3.org/2000/svg", "g");
                const g2 = document.createElementNS("http://www.w3.org/2000/svg", "g");
                const g3 = document.createElementNS("http://www.w3.org/2000/svg", "g");


                const path1 = document.createElementNS("http://www.w3.org/2000/svg", "path");
                path1.setAttribute("d", "M170.667,85.333c23.573,0,42.667-19.093,42.667-42.667C213.333,19.093,194.24,0,170.667,0S128,19.093,128,42.667 C128,66.24,147.093,85.333,170.667,85.333z ");

                const path2 = document.createElementNS("http://www.w3.org/2000/svg", "path");
                path2.setAttribute("d", "M170.667,128C147.093,128,128,147.093,128,170.667s19.093,42.667,42.667,42.667s42.667-19.093,42.667-42.667 S194.24,128,170.667,128z ");

                const path3 = document.createElementNS("http://www.w3.org/2000/svg", "path");
                path3.setAttribute("d", "M170.667,256C147.093,256,128,275.093,128,298.667c0,23.573,19.093,42.667,42.667,42.667s42.667-19.093,42.667-42.667 C213.333,275.093,194.24,256,170.667,256z ");

                g3.appendChild(path1);
                g3.appendChild(path2);
                g3.appendChild(path3);
                g2.appendChild(g3);
                g1.appendChild(g2);
                svg.appendChild(g1);
                a.appendChild(svg);
                td.appendChild(a);

                const div = document.createElement("div");
                div.className = "action-option";

                const ul = document.createElement("ul");

                const li0 = document.createElement("li");
                const a0 = document.createElement("a");
                a0.href = "/course?pid="+data.data[i].pid;
                const i0 = document.createElement("i");
                i0.className = "far fa-file-alt mr-2";
                const text0 = document.createTextNode(" View");
                a0.appendChild(i0);
                a0.appendChild(text0);
                li0.appendChild(a0);
                ul.appendChild(li0);

                const li1 = document.createElement("li");
                const a1 = document.createElement("a");
                a1.href = "/project-modification?pid="+data.data[i].pid;
                const i1 = document.createElement("i");
                i1.className = "far fa-edit mr-2";
                const text1 = document.createTextNode("Edit");
                a1.appendChild(i1);
                a1.appendChild(text1);
                li1.appendChild(a1);
                ul.appendChild(li1);

                const li2 = document.createElement("li");
                const a2 = document.createElement("a");
                a2.href = "/project/api/deleteProject?pid="+data.data[i].pid;
                const i2 = document.createElement("i");
                i2.className = "far fa-trash-alt mr-2";
                const text2 = document.createTextNode("Delete");
                a2.appendChild(i2);
                a2.appendChild(text2);
                li2.appendChild(a2);
                ul.appendChild(li2);

                div.appendChild(ul);
                td.appendChild(div);


            }


        }
    )
}

function getMyVideos()
{

    let projectArea=document.getElementById("video-list");

    let url="/video/api/video-page?pid="+getQueryString("pid");

    $.get(url,function (data)
        {

            for (let i=0;i<data.data.length;++i) {

                var row = document.createElement("tr");
                projectArea.appendChild(row);
// Create and append the table data elements to the row
                var id = document.createElement("td");
                id.innerText = "#"+data.data[i].vid;
                row.appendChild(id);

                var name = document.createElement("td");
                name.className="col-sm-2 col-xs-2";
                name.innerHTML = "<a class='profile' href='/static/videos/"+data.data[i].video+"'>"+data.data[i].title+"</a>" ;
                row.appendChild(name);

                var profile = document.createElement("td");
                profile.className="col-sm-2 col-xs-2";
                profile.innerHTML = "<div class='profile'>"+data.data[i].message+"</div>";
                row.appendChild(profile);

                var date = document.createElement("td");
                date.className="nowrap";
                date.innerHTML =data.data[i].time.substring(0,10);
                row.appendChild(date);

                var amount = document.createElement("td");
                amount.innerText = data.data[i].amount;
                row.appendChild(amount);

                var status = document.createElement("td");
                var badge = document.createElement("label");

                if(data.data[i].status==0)
                {
                    badge.classList.add("mb-0", "badge", "badge-success");
                    badge.innerText = "access";
                }else
                {
                    badge.classList.add("mb-0", "badge", "badge-danger");
                    badge.innerText = "deny";
                }

                status.appendChild(badge);
                row.appendChild(status);

                const td = document.createElement("td");
                td.className = "relative";
                row.appendChild(td);
                const a = document.createElement("a");
                a.className = "action-btn";
                a.href = "javascript:void(0);";

                const svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
                svg.classList.add("default-size");
                svg.setAttribute("viewBox", "0 0 341.333 341.333");

                const g1 = document.createElementNS("http://www.w3.org/2000/svg", "g");
                const g2 = document.createElementNS("http://www.w3.org/2000/svg", "g");
                const g3 = document.createElementNS("http://www.w3.org/2000/svg", "g");


                const path1 = document.createElementNS("http://www.w3.org/2000/svg", "path");
                path1.setAttribute("d", "M170.667,85.333c23.573,0,42.667-19.093,42.667-42.667C213.333,19.093,194.24,0,170.667,0S128,19.093,128,42.667 C128,66.24,147.093,85.333,170.667,85.333z ");

                const path2 = document.createElementNS("http://www.w3.org/2000/svg", "path");
                path2.setAttribute("d", "M170.667,128C147.093,128,128,147.093,128,170.667s19.093,42.667,42.667,42.667s42.667-19.093,42.667-42.667 S194.24,128,170.667,128z ");

                const path3 = document.createElementNS("http://www.w3.org/2000/svg", "path");
                path3.setAttribute("d", "M170.667,256C147.093,256,128,275.093,128,298.667c0,23.573,19.093,42.667,42.667,42.667s42.667-19.093,42.667-42.667 C213.333,275.093,194.24,256,170.667,256z ");

                g3.appendChild(path1);
                g3.appendChild(path2);
                g3.appendChild(path3);
                g2.appendChild(g3);
                g1.appendChild(g2);
                svg.appendChild(g1);
                a.appendChild(svg);
                td.appendChild(a);

                const div = document.createElement("div");
                div.className = "action-option";

                const ul = document.createElement("ul");

                const li0 = document.createElement("li");
                const a0 = document.createElement("a");
                a0.href = "/watch?pid="+getQueryString("pid")+"&v="+data.data[i].vid;
                const i0 = document.createElement("i");
                i0.className = "far fa-file-alt mr-2";
                const text0 = document.createTextNode(" View");
                a0.appendChild(i0);
                a0.appendChild(text0);
                li0.appendChild(a0);
                ul.appendChild(li0);

                const li1 = document.createElement("li");
                const a1 = document.createElement("a");
                a1.href = "/project-modification?pid="+data.data[i].pid;
                const i1 = document.createElement("i");
                i1.className = "far fa-edit mr-2";
                const text1 = document.createTextNode("Edit");
                a1.appendChild(i1);
                a1.appendChild(text1);
                li1.appendChild(a1);
                ul.appendChild(li1);

                const li2 = document.createElement("li");
                const a2 = document.createElement("a");
                a2.href = "/video/api/delete-video?vid="+data.data[i].vid+"&pid="+getQueryString("pid");
                const i2 = document.createElement("i");
                i2.className = "far fa-trash-alt mr-2";
                const text2 = document.createTextNode("Delete");
                a2.appendChild(i2);
                a2.appendChild(text2);
                li2.appendChild(a2);
                ul.appendChild(li2);

                div.appendChild(ul);
                td.appendChild(div);


            }


        }
    )
}

var vid;
function getVideos()
{
    let videosArea=document.getElementById("videos-list");
    let url="/video/api/video-page";
    let pid=getQueryString("pid");
    vid=getQueryString("v");
    let videoName=null;

    if(pid)
        url=url+"?pid="+pid;
    $.get(url,function (data)
        {

            for (let i=0;i<data.data.length;++i) {
                if(data.data[i].status==1)
                    continue;
                if(vid==data.data[i].vid) {
                    videoName = data.data[i].video;
                    document.getElementById("video-message").innerText=data.data[i].message;
                }
                let li=document.createElement("li");
                let a=document.createElement("a");
                a.href="/watch?pid="+pid+"&v="+data.data[i].vid;
                a.innerText=data.data[i].title;
                li.appendChild(a);
                videosArea.appendChild(li);
            }

                document.getElementById("video-source").src="/static/videos/"+videoName;

            if(videoName)
                getVideoComments(vid,"comments");


        }
    )
}

function getPdfs()
{
    let pdfsArea=document.getElementById("pdf-list");
    let pid=getQueryString("pid");
    let url="/pdf/api/pdf-page?pid="+pid;
    $.get(url,function (data)
        {

            for (let i=0;i<data.data.length;++i) {
                let tr=document.createElement("tr");
                let td1=document.createElement("td");
                td1.innerText=data.data[i].title;
                let td2=document.createElement("td");
                let a=document.createElement("a");
                a.href="/static/pdfs/"+data.data[i].pdf;
                a.innerText=data.data[i].pdf;
                td2.appendChild(a);
                let td3=document.createElement("td");
                td3.innerText=data.data[i].time;
                tr.appendChild(td1);
                tr.appendChild(td2);
                tr.appendChild(td3);
                pdfsArea.appendChild(tr);

            }



        }
    )
}
function dateTimeLessThanCurrentTime(timeStr)
{
    var now = new Date();

// 将给定时间字符串转换为Date对象
    var givenTime = new Date(timeStr);

// 比较两个时间
    if (now.getTime() > givenTime.getTime())
        return true;
    else
        return false;
}

function getExaminations()
{
    let examinationArea=document.getElementById("examination-list");
    let pid=getQueryString("pid");
    let url="/examination/api/examination-list?pid="+pid;
    $.get(url,function (data)
        {

            for (let i=0;i<data.data.length;++i) {
                var row = document.createElement("tr");
                examinationArea.appendChild(row);
// Create and append the table data elements to the row
                var id = document.createElement("td");
                id.innerText = "#"+data.data[i].eid;
                row.appendChild(id);

                var name = document.createElement("td");
                name.className="col-sm-2 col-xs-2";
                name.innerHTML = data.data[i].title;
                row.appendChild(name);



                var date = document.createElement("td");
                date.className="nowrap";
                date.innerHTML =data.data[i].startTime;
                row.appendChild(date);

                var profile = document.createElement("td");
                profile.className="nowrap";
                profile.innerHTML =data.data[i].deadTime;
                row.appendChild(profile);

                var status = document.createElement("td");
                var badge = document.createElement("label");

                if(dateTimeLessThanCurrentTime(data.data[i].startTime)&&!dateTimeLessThanCurrentTime(data.data[i].deadTime))
                {

                    badge.classList.add("mb-0", "badge", "badge-success");
                    badge.innerText = "正在进行考试";
                }else
                {
                    badge.classList.add("mb-0", "badge", "badge-danger");
                    badge.innerText = "不在考试时间";
                }

                status.appendChild(badge);
                row.appendChild(status);

                const td = document.createElement("td");
                td.className = "relative";
                row.appendChild(td);
                const a = document.createElement("a");
                a.className = "action-btn";
                a.href = "javascript:void(0);";

                const svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
                svg.classList.add("default-size");
                svg.setAttribute("viewBox", "0 0 341.333 341.333");

                const g1 = document.createElementNS("http://www.w3.org/2000/svg", "g");
                const g2 = document.createElementNS("http://www.w3.org/2000/svg", "g");
                const g3 = document.createElementNS("http://www.w3.org/2000/svg", "g");


                const path1 = document.createElementNS("http://www.w3.org/2000/svg", "path");
                path1.setAttribute("d", "M170.667,85.333c23.573,0,42.667-19.093,42.667-42.667C213.333,19.093,194.24,0,170.667,0S128,19.093,128,42.667 C128,66.24,147.093,85.333,170.667,85.333z ");

                const path2 = document.createElementNS("http://www.w3.org/2000/svg", "path");
                path2.setAttribute("d", "M170.667,128C147.093,128,128,147.093,128,170.667s19.093,42.667,42.667,42.667s42.667-19.093,42.667-42.667 S194.24,128,170.667,128z ");

                const path3 = document.createElementNS("http://www.w3.org/2000/svg", "path");
                path3.setAttribute("d", "M170.667,256C147.093,256,128,275.093,128,298.667c0,23.573,19.093,42.667,42.667,42.667s42.667-19.093,42.667-42.667 C213.333,275.093,194.24,256,170.667,256z ");

                g3.appendChild(path1);
                g3.appendChild(path2);
                g3.appendChild(path3);
                g2.appendChild(g3);
                g1.appendChild(g2);
                svg.appendChild(g1);
                a.appendChild(svg);
                td.appendChild(a);

                const div = document.createElement("div");
                div.className = "action-option";

                const ul = document.createElement("ul");

                const li0 = document.createElement("li");
                const a0 = document.createElement("a");
                a0.href = "/question-management?eid="+data.data[i].eid;
                const i0 = document.createElement("i");
                i0.className = "far fa-file-alt mr-2";
                const text0 = document.createTextNode(" View");
                a0.appendChild(i0);
                a0.appendChild(text0);
                li0.appendChild(a0);
                ul.appendChild(li0);

                const li1 = document.createElement("li");
                const a1 = document.createElement("a");
                a1.href = "/edit-examination?eid="+data.data[i].eid;
                const i1 = document.createElement("i");
                i1.className = "far fa-edit mr-2";
                const text1 = document.createTextNode("Edit");
                a1.appendChild(i1);
                a1.appendChild(text1);
                li1.appendChild(a1);
                ul.appendChild(li1);

                const li2 = document.createElement("li");
                const a2 = document.createElement("a");
                a2.href = "/project/api/deleteProject?pid="+data.data[i].pid;
                const i2 = document.createElement("i");
                i2.className = "far fa-trash-alt mr-2";
                const text2 = document.createTextNode("Delete");
                a2.appendChild(i2);
                a2.appendChild(text2);
                li2.appendChild(a2);
                ul.appendChild(li2);

                div.appendChild(ul);
                td.appendChild(div);
            }
        }
    )
}

function getStudentExaminations()
{
    let examinationArea=document.getElementById("examination-list");
    let pid=getQueryString("pid");
    let url="/examination/api/examination-list?pid="+pid;
    $.get(url,function (data)
        {

            for (let i=0;i<data.data.length;++i) {
                var row = document.createElement("tr");
                examinationArea.appendChild(row);
// Create and append the table data elements to the row


                var name = document.createElement("td");
                name.className="col-sm-2 col-xs-2";
                name.innerHTML = data.data[i].title;
                row.appendChild(name);



                var date = document.createElement("td");
                date.className="nowrap";
                date.innerHTML =data.data[i].startTime;
                row.appendChild(date);

                var profile = document.createElement("td");
                profile.className="nowrap";
                profile.innerHTML =data.data[i].deadTime;
                row.appendChild(profile);

                var status = document.createElement("td");
                var badge = document.createElement("a");

                if(dateTimeLessThanCurrentTime(data.data[i].startTime)&&!dateTimeLessThanCurrentTime(data.data[i].deadTime))
                {
                    badge.href="examination?eid="+data.data[i].eid;
                    badge.classList.add("mb-0", "badge", "badge-success");
                    badge.innerText = "开始考试";
                }else
                {
                    badge.href="javascript:void(0);"
                    badge.classList.add("mb-0", "badge", "badge-danger");
                    badge.innerText = "还没开始";
                }

                status.appendChild(badge);
                row.appendChild(status);

            }
        }
    )
}
function getVideoComments(xid,subUrl)
{
    let commentArea=document.getElementById("comment-area");
    let url="/comment/api/"+subUrl
    if(subUrl==="comments")
        url=url+"?vid="+xid;
    else
        url=url+"?pid="+xid;
    $.get(url,function (data)
        {
            for (let i=0;i<data.data.length;++i) {

                let div1=document.createElement("div");
                div1.className="media-div"
                let div2=document.createElement("div");
                div2.className="media";
                div1.appendChild(div2);

                let div3=document.createElement("div");
                div3.className=" ms-11 col-sm-11 col-lg-11 col-xl-11 col-md-11 col-xs-11";

                let img2=document.createElement("img");
                img2.src="/static/portraits/"+data.data[i].portrait;
                img2.className="media-portrait col-sm-1 col-lg-1 col-xl-1 col-md-1 col-xs-1";
                let div8=document.createElement("div");
                div2.appendChild(div8);
                div8.appendChild(img2);
                div8.appendChild(div3);

                let div5=document.createElement("div");

                div5.innerText=data.data[i].name;
                let div4=document.createElement("div");
                div4.className="media-comment";
                div4.innerText=data.data[i].comment;
                let a2=document.createElement("a");
                a2.href="/space?uid="+data.data[i].uid;

                a2.appendChild(div5);

                let span1=document.createElement("span");
                span1.className="media-extra-message";




                span1.className="time-font col-sm-10";
                span1.innerText=timeStampTransform(data.data[i].commentTime);




                div3.appendChild(a2);
                div3.appendChild(div4);
                div3.appendChild(span1);


                if($.cookie('uid')==data.data[i].uid)
                {
                    var svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
                    svg.setAttribute("viewBox", "0 0 24 24");
                    svg.setAttribute("class", "default-size");

                    let a3=document.createElement("a");
                    a3.setAttribute("onclick","deleteVideoComment("+data.data[i].cid+")");

// 创建路径元素
                    var path = document.createElementNS("http://www.w3.org/2000/svg", "path");
                    path.setAttribute("d", "M16 6V4.5C16 3.12 14.88 2 13.5 2h-3C9.11 2 8 3.12 8 4.5V6H3v2h1.06l.81 11.21C4.98 20.78 6.28 22 7.86 22h8.27c1.58 0 2.88-1.22 3-2.79L19.93 8H21V6h-5zm-6-1.5c0-.28.22-.5.5-.5h3c.27 0 .5.22.5.5V6h-4V4.5zm7.13 14.57c-.04.52-.47.93-1 .93H7.86c-.53 0-.96-.41-1-.93L6.07 8h11.85l-.79 11.07zM9 17v-6h2v6H9zm4 0v-6h2v6h-2z");

// 将路径元素添加到SVG元素中
                    svg.appendChild(path);
                    a3.appendChild(svg);
                    div3.appendChild(a3);
                }
                var svg_1 = document.createElementNS("http://www.w3.org/2000/svg", "svg");
                svg_1.setAttribute("viewBox", "0 0 24 24");
                svg_1.setAttribute("class", "default-size");

                let a3_1=document.createElement("a");
                a3_1.setAttribute("onclick","replyAreaShow("+data.data[i].cid+")");


// 创建路径元素
                var path_1 = document.createElementNS("http://www.w3.org/2000/svg", "path");
                path_1.setAttribute("d", "M1.751 10c0-4.42 3.584-8 8.005-8h4.366c4.49 0 8.129 3.64 8.129 8.13 0 2.96-1.607 5.68-4.196 7.11l-8.054 4.46v-3.69h-.067c-4.49.1-8.183-3.51-8.183-8.01zm8.005-6c-3.317 0-6.005 2.69-6.005 6 0 3.37 2.77 6.08 6.138 6.01l.351-.01h1.761v2.3l5.087-2.81c1.951-1.08 3.163-3.13 3.163-5.36 0-3.39-2.744-6.13-6.129-6.13H9.756z");
// 将路径元素添加到SVG元素中
                svg_1.appendChild(path_1);
                a3_1.appendChild(svg_1);
                div3.appendChild(a3_1);


                commentArea.appendChild(div1);
                getVideoCommentReplies(data.data[i].cid,"video-replies-by-cid",div1);


            }


        }
    )
}


function getVideoCommentReplies(cid,subUrl,commentArea)
{


    let url="/reply/api/"+subUrl

        url=url+"?cid="+cid;
    $.get(url,function (data)
        {
            for (let i=0;i<data.data.length;++i) {

                let div1=document.createElement("div");
                div1.className="media-div col-sm-offset-1";
                div1.style="border-left:1px solid #e5e9ef";
                let div2=document.createElement("div");
                div2.className="media";

                div1.appendChild(div2);

                let div3=document.createElement("div");
                div3.className=" ms-11 col-sm-11 col-lg-11 col-xl-11 col-md-11 col-xs-11";

                let img2=document.createElement("img");
                img2.src="/static/portraits/"+data.data[i].portrait;
                img2.className="media-portrait col-sm-1 col-lg-1 col-xl-1 col-md-1 col-xs-1";
                let div8=document.createElement("div");
                div2.appendChild(div8);
                div8.appendChild(img2);
                div8.appendChild(div3);

                let div5=document.createElement("div");

                div5.innerText=data.data[i].name;
                let div4=document.createElement("div");
                div4.className="media-comment";
                div4.innerText=data.data[i].reply;
                let a2=document.createElement("a");
                a2.href="/space?uid="+data.data[i].uid;

                a2.appendChild(div5);

                let span1=document.createElement("span");
                span1.className="media-extra-message";




                span1.className="time-font col-sm-10";
                span1.innerText=timeStampTransform(data.data[i].replyTime);



                div3.appendChild(a2);
                div3.appendChild(div4);
                div3.appendChild(span1);


                if($.cookie('uid')==data.data[i].uid)
                {
                    var svg = document.createElementNS("http://www.w3.org/2000/svg", "svg");
                    svg.setAttribute("viewBox", "0 0 24 24");
                    svg.setAttribute("class", "default-size");

                    let a3=document.createElement("a");
                    a3.setAttribute("onclick","deleteVideoReply("+data.data[i].rid+")");


// 创建路径元素
                    var path = document.createElementNS("http://www.w3.org/2000/svg", "path");
                    path.setAttribute("d", "M16 6V4.5C16 3.12 14.88 2 13.5 2h-3C9.11 2 8 3.12 8 4.5V6H3v2h1.06l.81 11.21C4.98 20.78 6.28 22 7.86 22h8.27c1.58 0 2.88-1.22 3-2.79L19.93 8H21V6h-5zm-6-1.5c0-.28.22-.5.5-.5h3c.27 0 .5.22.5.5V6h-4V4.5zm7.13 14.57c-.04.52-.47.93-1 .93H7.86c-.53 0-.96-.41-1-.93L6.07 8h11.85l-.79 11.07zM9 17v-6h2v6H9zm4 0v-6h2v6h-2z");

// 将路径元素添加到SVG元素中
                    svg.appendChild(path);
                    a3.appendChild(svg);
                    div3.appendChild(a3);
                }


                commentArea.insertAdjacentElement('beforeend', div1);



            }


        }
    )

}


function replyAreaShow(cid) {
    // 弹出评论弹窗
    var form = $('<form>', {
        'id': 'comment-form',
        'class': 'p-3',
        'style':"z-index:3"
    }).append($('<div>', {
        'class': 'mb-3'
    }).append($('<textarea>', {
        'id':'reply-text',
        'name': 'content',
        'class': 'form-control',
        'style':'width:600px;resize:none',
        'placeholder': '请输入评论内容'
    }))).append($('<div>', {
        'class': 'd-flex justify-content-end'
    }).append($('<div>', {
        'type': 'submit',
        'class': 'btn btn-primary me-2',
        'onclick': 'submitReply('+cid+")"
    }).text('提交')).append($('<button>', {
        'type': 'button',
        'class': 'btn btn-danger',
        'onclick': '$.colorbox.close()'
    }).text('取消')));

    $.colorbox({
        html: form,
        width: 'auto',
        height: 'auto',
        overlayClose: false,

    });

}
function submitReply(cid)
{
    var reply = $('#reply-text').val();
    $.post("/reply/api/up-video-reply",{
        cid:cid,
        reply:reply,
    },function (data)
        {
            $.colorbox.close();
            clearArea("comment-area");
            getVideoComments(vid,"comments");
         //   location.reload();
        }
    )


}

function timeStampTransform(time)
{

    let date=new Date(Date.now()-new Date(time));

    console.log(date.getUTCDate()+" "+date.getUTCMonth());
    if(date.getUTCDate()===1&&date.getUTCMonth()===0) {
        if (date.getUTCHours() === 0) {
            if (date.getMinutes() === 0) {
                return  date.getUTCSeconds() + "秒前";
            } else {
                return date.getUTCMinutes() + "分钟前";
            }

        } else {
            return date.getUTCHours() + "小时前";
        }

    }else
    {
        return timestampToChineseTime(time);
    }
}
function deleteVideoComment(cid)
{
    $.get("/comment/api/delete-video-comment?cid="+cid,function (data)
        {
            clearArea("comment-area");
            getVideoComments(vid,"comments");

           // location.reload();
        }
    );
}
function deleteVideoReply(rid)
{
    $.get("/reply/api/delete-video-reply?rid="+rid,function (data)
        {
            clearArea("comment-area");
            getVideoComments(vid,"comments");

            // location.reload();
        }
    );
}
function getStudentList()
{
    let commentArea=document.getElementById("student-list");
    let url="/relationship/api/student-list";

    $.get(url,function (data)
        {
            for (let i=0;i<data.data.length;++i) {

                let div1=document.createElement("div");
                div1.className="media-div"
                let div2=document.createElement("div");
                div2.className="media";
                div1.appendChild(div2);

                let div3=document.createElement("div");
                div3.className=" ms-11 col-sm-11 col-lg-11 col-xl-11 col-md-11 col-xs-11";

                let img2=document.createElement("img");
                img2.src="/static/portraits/"+data.data[i].portrait;
                img2.className="media-portrait col-sm-1 col-lg-1 col-xl-1 col-md-1 col-xs-1";
                let div8=document.createElement("div");
                div2.appendChild(div8);
                div8.appendChild(img2);
                div8.appendChild(div3);

                let div5=document.createElement("div");

                div5.innerText=data.data[i].name;
                let div4=document.createElement("div");
                div4.className="media-comment col-sm-9";
                div4.innerText=data.data[i].profile;

                let a1=document.createElement("a");
                a1.className="btn btn-danger col-sm-2";
                a1.innerText="delete";
                a1.href="/relationship/api/delete-relationship?student="+data.data[i].uid;
                let a1_2=document.createElement("a");
                a1_2.className="btn btn-danger col-sm-2";
                a1_2.innerText="view progress";
                a1_2.href="/course-history?uid="+data.data[i].uid;





                let a2=document.createElement("a");
                a2.href="/space?uid="+data.data[i].uid;

                a2.appendChild(div5);

                let div6=document.createElement("div");
                div6.className="media-extra-message";






                div6.className="time-font";
                div6.innerText=timeStampTransform(data.data[i].time);
                let row=document.createElement("div");
                row.className="row";
                row.appendChild(a2);
                row.appendChild(div4)
                row.appendChild(a1)
                row.appendChild(a1_2)
                div3.appendChild(row);

                div3.appendChild(div6);
                commentArea.appendChild(div1);
            }
        }
    )
}

function generatePagination(start,end,selected)
{
    let pagination=document.getElementById("pagination");
    let firstLi=document.createElement("li");
    firstLi.className="page-item";
    let firstA=document.createElement("a");
    firstA.className="page-link";
    firstA.href="/index?"+(getQueryString("search")?("search="+getQueryString("search")+"&"):"")+"page="+(start===1?1:start-1);
    firstA.innerHTML="<i class='fas fa-chevron-left'></i> Older";
    firstLi.appendChild(firstA);
    pagination.appendChild(firstLi);
    if(end-start>5)
    {
        for(let i=start;i<start+3;i++)
        {
            let li=document.createElement("li");
            if(i==selected)
                li.className="page-item active";
            else
                li.className="page-item";
            let a=document.createElement("a");
            a.className="page-link";
            a.href="/index?"+(getQueryString("search")?("search="+getQueryString("search")+"&"):"")+"page="+i;
            a.innerText=i;
            li.appendChild(a);
            pagination.appendChild(li);
        }
        let midLi=document.createElement("li");
        midLi.className="page-item";
        let midA=document.createElement("a");
        midA.className="page-link";
        midA.href="/index?"+(getQueryString("search")?("search="+getQueryString("search")+"&"):"")+"page="+(start+3);
        midA.innerText="...";
        midLi.appendChild(midA);
        pagination.appendChild(midLi);

        let endLi=document.createElement("li");
        endLi.className="page-item";
        let endA=document.createElement("a");
        endA.className="page-link";
        endA.href="/index?"+(getQueryString("search")?("search="+getQueryString("search")+"&"):"")+"page="+(end-1);
        endA.innerText=""+(end-1);
        endLi.appendChild(endA);
        pagination.appendChild(endLi);

    }else
    {
        for(let i=start;i<end;i++)
        {

            let li=document.createElement("li");
            if(i==selected)
                li.className="page-item active";
            else
                li.className="page-item";
            let a=document.createElement("a");
            a.className="page-link";
            a.href="/index?"+(getQueryString("search")?("search="+getQueryString("search")+"&"):"")+"page="+i;
            a.innerText=i;
            li.appendChild(a);
            pagination.appendChild(li);
        }
    }

    let lastLi=document.createElement("li");
    lastLi.className="page-item";
    let lastA=document.createElement("a");
    lastA.className="page-link";
    lastA.href="/index?"+(getQueryString("search")?("search="+getQueryString("search")+"&"):"")+"page="+(end-1);
    lastA.innerHTML="<i class='fas fa-chevron-right'></i> Next";
    lastLi.appendChild(lastA);
    pagination.appendChild(lastLi);

}
function getPagination()
{
    $.get("project/api/project-page-num",function (data){
        let end=parseInt((data.data-1)/12)+2;
        let page=getQueryString("page");
        page=page?page:1
        generatePagination(page>3?page:1,end,page)

    });

}
function timestampToChineseTime(time)
{
    const date = new Date(time);
    const options = {
        year: 'numeric',
        month: '2-digit',
        day: '2-digit',
        hour12: false,
        hour: '2-digit',
        minute: '2-digit',
        second: '2-digit',
        timeZone: 'Asia/Shanghai'
    };
    const formattedDate = date.toLocaleString('zh-CN', options).replace(/(\d{4})\/(\d{2})\/(\d{2})/gi, '$1-$2-$3').replace(/,/gi, '');
    return formattedDate;
}
function getFirstVid()
{
    $.get("video/api/first-vid?pid="+getQueryString("pid"),function (data)
        {
            let btn=document.getElementById("course-video-list");
            if (data)
            {
                btn.href+="&v="+data;
            }else
            {
                btn.title="no video";
                btn.href="javascript:void(0)";
            }
        }
    )
}
function generateUserCode()
{
    $.get("relationship/api/generate-user-code",function (data)
        {
            let area=document.getElementById("user-code");
            if (data)
            {
                area.innerText=data.data;
                if (confirm("是否分享到推特")) {
                    location.assign("https://twitter.com/intent/tweet?hashtags=程序设计精品视频网站教室邀请码&text="+data.data);
                }
            }
        }
    )
}
function getUserCode()
{
    $.get("relationship/api/user-code",function (data)
        {
            let area=document.getElementById("user-code");
            if (data)
            {
                area.innerText=data.data;
            }
        }
    )
}
function addTeacherByCode()
{
    let code=document.getElementById("code-input").value;
    $.get("relationship/api/add-teacher-by-user-code?code="+code,function (data)
        {
            let area=document.getElementById("teacher-area");
            alert(data.data);
            if (data)
            {

                if (data.data) {

                    let div1=document.createElement("div");
                    div1.className="media-div"
                    let div2=document.createElement("div");
                    div2.className="media";
                    div1.appendChild(div2);

                    let div3=document.createElement("div");
                    div3.className=" ms-11 col-sm-11 col-lg-11 col-xl-11 col-md-11 col-xs-11";

                    let img2=document.createElement("img");
                    img2.src="/static/portraits/"+data.data.portrait;
                    img2.className="media-portrait col-sm-1 col-lg-1 col-xl-1 col-md-1 col-xs-1";
                    let div8=document.createElement("div");
                    div2.appendChild(div8);
                    div8.appendChild(img2);
                    div8.appendChild(div3);

                    let div5=document.createElement("div");

                    div5.innerText=data.data.name;
                    let div4=document.createElement("div");
                    div4.className="media-comment";
                    div4.innerText=data.data.profile;
                    let a2=document.createElement("a");
                    a2.href="/space?uid="+data.data.uid;
                    a2.appendChild(div5);
                    div3.appendChild(a2);
                    div3.appendChild(div4);
                    area.appendChild(div1);

                }
            }
        }
    )
}
function submitExamination(url)
{

    // 获取表单中各个字段的值
    var title = $('#title').val();
    var startTime = $('#start-time').val();
    var deadTime = $('#dead-time').val();

    if(title==""||startTime==""||deadTime=="")
    {
        alert("null");
        return;
    }

    // 发送post请求
    $.ajax({
        url: '/examination/api/up-examination',
        type: 'POST',
        data: {
            title: title,
            startTime: startTime,
            deadTime: deadTime,
            pid:getQueryString("pid")
        },
        success: function(data){
            // 成功返回结果后的处理
            alert('提交成功！'+data.data);
        },
        error: function(){
            // 失败时的处理
            alert('提交失败！');
        }
    });
}

function editExamination()
{

    // 获取表单中各个字段的值
    var title = $('#title').val();
    var startTime = $('#start-time').val();
    var deadTime = $('#dead-time').val();

    if(title==""||startTime==""||deadTime=="")
    {
        alert("null");
        return;
    }

    // 发送post请求
    $.ajax({
        url: '/examination/api/edit-examination',
        type: 'POST',
        data: {
            title: title,
            startTime: startTime,
            deadTime: deadTime,
            eid:getQueryString("eid")
        },
        success: function(data){
            // 成功返回结果后的处理
            alert('提交成功！'+data.data);
        },
        error: function(){
            // 失败时的处理
            alert('提交失败！');
        }
    });
}

function submitChoiceQuestion()
{
    var title = $('#problem-title').val();
    var optionA = $('#A').val();
    var optionB = $('#B').val();
    var optionC = $('#C').val();
    var optionD = $('#D').val();
    var answer = $('input[name="choice-question"]:checked').val();
    var notes = $('#additional-msg').val()?$('#additional-msg').val():"无";
    if (title == "" || optionA == "" || optionB == "" || optionC == "" || optionD == "" || !answer ) {
        // 如果有任何一个字段为空，显示错误消息
        alert("请填写所有字段。");
        // 取消表单提交
        return;
    }

    $.ajax({
        type: 'POST',
        url: '/question/api/up-choice-question',
        data: {
            'eid':getQueryString("eid"),
            'title' : title,
            'optionA' : optionA,
            'optionB' : optionB,
            'optionC' : optionC,
            'optionD' : optionD,
            'answer' : answer,
            'notes' : notes
        },

        success: function(response) {
            alert('提交成功！');
        },
        error: function(error) {
            alert('提交失败：' + error.message);
        }
    });

}
function submitTrueFalseQuestion()
{
    var title = $('#problem-title-2').val();
    var answer = $('input[name="true-false-question"]:checked').val();
    var note = $('#additional-msg-2').val();

    $.ajax({
        url: '/question/api/up-true-false-question', // 表单提交的URL
        type: 'POST',
        data: {
            'eid':getQueryString("eid"),
            'title': title,
            'answer': answer,
            'notes': note
        },
        success: function(response) {
            // 处理成功响应
            alert(response.message);
        },
        error: function(error) {
            // 处理错误响应
            alert(error.message);
        }
    });

}

function submitQuestion()
{
    var title = $('#problem-title-3').val();
    var notes = $('#additional-msg-3').val();
    var score=$('#problem-score').val();
    $.ajax({
        url: '/question/api/up-question',
        type: 'POST',
        data: {
            eid:getQueryString("eid"),
            title: title,
            score:score,
            notes: notes
        },
        success: function(response) {
            console.log(response);
        },
        error: function(xhr, status, error) {
            console.error(error);
        }
    });

}
function clearArea(id)
{
    var parent = document.getElementById(id);  // 获取父节点
    while (parent.firstChild) {  // 循环删除所有子元素
        parent.removeChild(parent.firstChild);
    }
}
function submitAccountInformation() {
    // 获取表单中的值
    var name = $('#form-name').val();
    var password = $('#form-password').val();
    var email = $('#form-email').val();
    var role = $('#form-role').val();

    if(name&&password&&email&&role)
    {
        // 发送 POST 请求
        $.post('/auth/api/add-account', {
            name: name,
            password: password,
            email: email,
            role: role
        }, function(data, status) {
            if (status === 'success') {
                // 请求成功后的操作
                alert('提交成功！');
                location.reload();
            } else {
                // 请求失败后的操作
                alert('提交失败！');
            }
        });
    }else
    {
        alert('表单不允许为空');
    }
}
function editAccountInformationUserVersion() {
    // 获取表单中的值
    var name = $('#form-name').val();
    var password = $('#form-password').val();
    var code = $('#form-code').val();

    if(name&&password&&code)
    {

            $.post('/auth/api/edit-account-user-version', {
                name: name,
                password: password,
               code: code,
            }, function(data, status) {
                if (status === 'success') {
                    // 请求成功后的操作
                    alert('提交成功！');
                    location.reload();
                } else {
                    // 请求失败后的操作
                    alert('提交失败！');
                }
            });

        // 发送 POST 请求
    }else
    {
        alert('表单不允许为空');
    }
}
function editAccountInformation() {
    // 获取表单中的值
    var name = $('#form-name').val();
    var password = $('#form-password').val();
    var email = $('#form-email').val();
    var role = $('#form-role').val();

    if(name&&password&&email&&role)
    {
        // 发送 POST 请求
        $.post('/auth/api/edit-account', {
            name: name,
            password: password,
            email: email,
            role: role,
            uid:getQueryString("uid")
        }, function(data, status) {
            if (status === 'success') {
                // 请求成功后的操作
                alert('提交成功！');
                location.reload();
            } else {
                // 请求失败后的操作
                alert('提交失败！');
            }
        });
    }else
    {
        alert('表单不允许为空');
    }

}

function getAccountInformation() {
        // 发送 POST 请求
    let url="/auth/api/account-information-by-uid";
        if(getQueryString("uid"))
        {
            url=url+"?uid="+getQueryString("uid");
        }

        $.get(url, function(data) {
            $('#form-name').val(data.data.account);
            $('#form-email').val(data.data.mail);
            $('#form-role').val(data.data.role);
        });

}


function submitChoiceQuestionScore(table,id)
{
    var score = $('#'+id).val();

    $.ajax({
        url: '/question/api/edit-question-score',
        type: 'GET',
        data: {
            eid:getQueryString("eid"),
            score: score,
            table:table
        },
        success: function(response) {
            location.reload();
        },
        error: function(xhr, status, error) {
            console.error(error);
        }
    });

}
function setAddButton()
{
    let pid=getQueryString("pid");
    document.getElementById("examination-add-btn").href="/up-examination?pid="+pid;
    document.getElementById("pdf-add-btn").href="/up-resource?pid="+pid;
    document.getElementById("video-add-btn").href="/up-video?pid="+pid;
}

function deleteQuestion(cqid,table)
{
    $.get("/question/api/delete-question?cqid="+cqid+"&table="+table,function (data)
        {

            location.replace(location.href+"&delete="+data.data);
        }
    )

}

function submitMailCode()
{
    const data = {
        email: $('#form-email').val(),
        code: $('#new-code').val(),
    };

// 发送 POST 请求
    $.post("/auth/api/change-email", data, function(response) {
        // 处理响应结果
        location.reload();
    });


}
function getVerifyCode()
{
    var verifyCode=document.getElementById("verifyCodeImg");
    verifyCode.src="auth/api/kaptcha?"+Date.now();
}

function getMailCode() {
    if (document.getElementById("form-email").value) {
        $.get("/auth/api/mail_code?mail=" + document.getElementById("form-email").value,
            function (data) {
                alert(data.message);
            }
        )
    }
    else
    {
        alert("邮箱为空");
    }
}

function submitTeacherCode() {
    if (document.getElementById("code-input").value) {
        $.get("/TeacherCode/api/use-teacher-code?code=" + document.getElementById("code-input").value,
            function (data) {
                if(data.data)
                {
                    alert("已成功成为教师，请重新登录");
                    location.replace("/logout")
                }else
                {
                    alert("error");
                }
            }
        )
    }
    else
    {
        alert("输入为空");
    }

}

function setSearchInput(areaId,inputId,col)
{
    var searchInput = document.getElementById(inputId);
    var table = document.getElementById(areaId);



    // 给输入框添加输入事件监听器
    searchInput.addEventListener("input", function() {
        // 获取输入框的值，并转换为小写字母
        var value = searchInput.value.toLowerCase();



        // 遍历表格中的每一行
        for (var i = 0; i < table.rows.length; i++) {
            var row = table.rows[i];
            var name = row.cells[col].textContent.toLowerCase();

            // 如果行中的第一列包含输入框的值，显示行，否则隐藏行
            if (name.indexOf(value) > -1) {
                row.style.display = "";
            } else {
                row.style.display = "none";
            }
        }
    });
}

function setSearchInput(areaId,inputId,col1,col2)
{

    var searchInput = document.getElementById(inputId);
    var table = document.getElementById(areaId);



    // 给输入框添加输入事件监听器
    searchInput.addEventListener("input", function() {
        // 获取输入框的值，并转换为小写字母
        var value = searchInput.value.toLowerCase();



        // 遍历表格中的每一行
        for (var i = 0; i < table.rows.length; i++) {
            var row = table.rows[i];
            var name = row.cells[col1].textContent.toLowerCase();
            if (typeof col2 === 'undefined')
            {
                if (name.indexOf(value) > -1) {
                    row.style.display = "";
                } else {
                    row.style.display = "none";
                }
            }else
            {
                var name2 = row.cells[col2].textContent.toLowerCase();

                // 如果行中的第一列包含输入框的值，显示行，否则隐藏行
                if (name.indexOf(value) > -1||name2.indexOf(value) > -1) {
                    row.style.display = "";
                } else {
                    row.style.display = "none";
                }
            }


        }
    });
}