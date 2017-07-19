<?php
namespace Core\Libraries\APP;

class Controller extends \Core\MyCore
{
    public function preDo(){}
    public function loadModel($name)
    {
        $res = explode('_', $name);
        $res = is_array($res) ? implode('/', $res) : $res;
        $modelClass = '\Application\Model\\'.str_replace('/', '\\', $res).'Model';
        $path = APPPATH.'/Model/'.$res.'.'.'php';
        require_once $path;
        return new $modelClass;
    }

    public function error($message, $url)
    {
        $this->assign('msg', $message);
        $this->assign('url', $url);
        $this->display('errorpage.html');
    }
}