<?php
​
class EPC
{
​
    function convertToEAN($epc)
    {
        $binepc = str_pad($this->str_baseconvert($epc, 16, 2), 96, "0", STR_PAD_LEFT);
        if (strlen($binepc) == 96) {
            $this->error = "";
            $company = $this->str_baseconvert(substr($binepc, 14, 20), 2, 10);
            $indicator = $this->str_baseconvert(substr($binepc, 34, 4), 2, 10);
            $itemref = $this->str_baseconvert(substr($binepc, 38, 20), 2, 10);
            $serial = $this->str_baseconvert(substr($binepc, 58, 38), 2, 10);
            $tempean = $indicator . $company . $itemref;
            $checksum = $this->calculateChecksum($tempean);
            $ean = $company . $itemref . $checksum;
            return $ean;
            // echo $serial."\n";
            /*
             * String tempean = indicator.toString()+company.toString()+itemref.toString();
             * int checksum = CalculateChecksum(tempean);
             * this.Ean = company.toString()+itemref.toString()+String.valueOf(checksum);
             * this.Serial = serial.toString();
             */
        } else {
            $this->error = "Invalid EPC Code";
        }
    }
​
    function calculateChecksum($ean)
    {
        $ean = str_pad($ean, 13, "0", STR_PAD_LEFT);
        if (strlen($ean) == 13) {
            return (10 - ((3 * ($ean[0] + $ean[2] + $ean[4] + $ean[6] + $ean[8] + $ean[10] + $ean[12]) + ($ean[1] + $ean[3] + $ean[5] + $ean[7] + $ean[9] + $ean[11])) % 10)) % 10;
        }
​
        return 0;
    }
​
    function str_baseconvert($str, $frombase = 10, $tobase = 36)
    {
        $str = trim($str);
        if (intval($frombase) != 10) {
            $len = strlen($str);
            $q = 0;
            for ($i = 0; $i < $len; $i ++) {
                $r = base_convert($str[$i], $frombase, 10);
                $q = bcadd(bcmul($q, $frombase), $r);
            }
        } else
            $q = $str;
​
        if (intval($tobase) != 10) {
            $s = '';
            while (bccomp($q, '0', 0) > 0) {
                $r = intval(bcmod($q, $tobase));
                $s = base_convert($r, 10, $tobase) . $s;
                $q = bcdiv($q, $tobase, 0);
            }
        } else
            $s = $q;
​
        return $s;
    }
}
​
?>