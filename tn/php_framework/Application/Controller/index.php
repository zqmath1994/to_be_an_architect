<?php

namespace Application\Controller;
use Core\Libraries\APP\Controller;

class IndexController extends Controller
{
    public function preDo()
    {
        $this->model = $this->loadModel('admin_admin');
    }
    public function index()
    {
        $page = isset($_GET['page']) ? $_GET['page'] :1;
        $pager = $this->model->setPager('/index/index?page=', $page, 'test');
        //$data = $this->model->getPageList($pager, 'test', '*', []);

        $this->assign('pager',$pager);
        $this->display('admin/index.html');

    }
}