<?php
namespace Application\Controller\admin;
use Core\Common\Func;
use Core\Libraries\APP\Controller;

class AdminController extends Controller
{
    public function preDo()
    {
        session_start();
        $this->func = new Func();
        $this->model = $this->loadModel('admin_admin');
    }
    public function index()
    {
        $page = isset($_POST['page']) ? $_POST['page'] : 1;
        $itemNum = 20;
        //$data = $this->model->getTitles()
        $this->display('test.html');
    }
    public function register()
    {
        $userName = $_POST['username'];
        $nickName = $_POST['nickname'];
        $password = $_POST['password'];
        $url = 'admin/login.html';
        if(!preg_match('/^[\x{4e00}-\x{9fa5}\w]+$/u',$nickName)){
            $this->error('请输入合法昵称nickname', $url);
            exit;
        }
        if(!preg_match("/^[0-9a-zA-Z_]+@[0-9a-zA-Z_]+.[0-9a-zA-Z_]+$/",$userName)){
            $this->error('请输入合法邮箱名email', $url);
            exit;
        }
        if(!preg_match('/^\w{6,20}$/', $password)){
            $this->error('非法的密码password', $url);
            exit;
        }
        $data = array('username'=>$userName, 'nickname'=>$nickName, 'password'=>$password, 'time'=>time());

        if($this->model->addUser($data) == false){
            echo "test"; exit;
            $this->error('注册失败', $url);
            exit;
        }else{
            $this->addSession(array('username'=>$userName, 'nickname'=>$nickName));
        }
    }
    public function login()
    {
        if($_POST){
            $userName = $_POST['username'];
            $password = $_POST['password'];
            $ret = $this->model->checkUser($userName, $password);
            if(false === $ret){
                exit('获取用户信息失败');
            }
            if(-1 === $ret){
                $this->error('用户尚未注册', 'admin/admin/register');
                exit;
            }
            if(0 === $ret){
                $this->error('用户名或密码错误', 'admin/admin/login');
                exit;
            }
            header('Location: /admin/admin/index');
        }
        $this->display('/admin/login.html');


    }
    public function addSession($data)
    {
        if(is_array($data)){
            foreach($data as $key => $val){
                $_SESSION[$key] = $val;
            }
            return true;
        }else{
            return false;
        }
    }
}