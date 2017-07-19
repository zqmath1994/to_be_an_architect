<?php
namespace Application\Model\admin;

use Core\Common\Db;
use Core\Libraries\APP\Model;

class indexModel extends Model
{
    public function __construct()
    {
        $this->db = Db::d('test');
    }
    public function getVal($table, $col, $where=array())
    {
        if(empty($table)){
            echo "表名不能为空";
            exit;
        }
        if(empty($col)){
            echo "请输入要查找的列名";
            exit;
        }
        return $this->db->select($table, $col, $where);
    }

    public function insertVal($table, $data)
    {
        $ret = $this->db->insert($table, $data);
        return $ret===false ? false : $this->id();
    }
}
