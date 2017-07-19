<?php
namespace Application\Model\admin;
use Core\Common\Db;
use Core\Libraries\APP\Model;

class adminModel extends Model
{
    public $table = 'user';
    public function __construct()
    {
        //$this->redis = $this->loadRedis('test');
        $this->db = Db::d('test');
    }
    public function getVal($col, $where=array())
    {
        if(!empty($col)){
            return $this->db->select($this->table, $col, $where);
        }else{
            return false;
        }
    }

    public function insertVal($data)
    {
        $ret = $this->db->insert($this->table, $data);
        return $ret===false ? false : $this->id();
    }

    public function checkUser($username, $password)
    {
        if(!empty($username) && !empty($password)){
            $ret = $this->db->select($this->table, array('id'), array('username'=>$username));
            if(!count($ret)){
                return -1;
            }
            $ret = $this->db->select($this->table, array('id'), array('username'=>$username, 'password'=>$password));
            if(count($ret)){
                return true;
            }else{
                return 0;
            }
        }
        return false;
    }

    public function addUser($data)
    {
        if(!empty($data)){
            $ret = $this->db->insert($this->table, $data);
            return $ret===false ? false : true;
        }else{
            return false;
        }
    }

    public function setPager($url, $currPage, $table)
    {
        $ret = $this->db->select($table, array('id'), array());
        $totalItems = (false === $ret) ? 0: count($ret);
        $pager = $this->loadPager()->setCurrPage($currPage)
            ->settotalItems($totalItems)
            ->setUrl($url)
            ->finish();
        return $pager;
    }
    public function test()
    {
        $ret = $this->db->select('test', '*', []);
        var_dump($ret);exit;
    }

    public function getPageList($pager, $table, $col, $where = array())
    {
        $start = ($pager->curr-1)*($pager->itemNum);
        $where['LIMIT'] = ["$start,$pager->itemNum"];
        $ret = $this->db->select('test', '*', $where);
        var_dump($ret);exit;
    }
}