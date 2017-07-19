<?php
namespace Core\Libraries\driver\log;
use Core\Common\Db;
class mysql
{
    public $db;
    public $table;
    public function __construct()
    {
        $this->db = Db::d('log');
        $this->table = 'logs';
    }
    public function log($message)
    {
        $data = ['message' => $message,
                 'time'    => date('Y-m-d H:i:s')
        ];
        $this->db->insert($this->table, $message);
    }
}