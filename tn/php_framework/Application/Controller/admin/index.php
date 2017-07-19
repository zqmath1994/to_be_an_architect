<?php
namespace Application\Controller\admin;

use Core\Libraries\APP\Controller;

class IndexController extends Controller
{
    public function preDo()
    {
        //echo "preDo";
    }
    public function index()
    {
        if(isset($_POST['test'])){
            $data = $_POST['test'];
            p($data);
            exit;
        }
        $this->display('test.html');
        //$this->assign('data',$data);
        //$this->display('admin/login.html');
    }
}