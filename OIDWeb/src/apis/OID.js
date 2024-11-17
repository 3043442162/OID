import http from '@/util/http'
// 获取OID树
export function getOIDTree(id,page,pageSize) {
    return http(
        {url:"/OID/userOwner",
        params: {
            id:id,
            page:page,
            pageSize:pageSize
        }}
        )
}
export function deleteOID(id) {
    return http.delete(
        "/OID?id="+id
    )
}
export function queryOIDMax(id){
    return http.get(
        "/OID/queryMaxOID?id="+id
    )
}
export function registerOID(node,fatherId){
    return http.post(
        "/OID/register?fatherId="+fatherId,
        node,{
            headers: {
                'Content-Type': 'application/json'
            }}
    )
}
export function updateOID(node){
    return http.post(
        "/OID/update",
        node,{
            headers: {
                'Content-Type': 'application/json'
            }}
    )
}
export function queryFatherMaxOID(id){
    return http.get(
        "/OID/queryFatherMaxOID?id="+id
    )
}
export function uploadXml(kvs, xmlName){
    return http.post(
        "/oidXml/upload?loginId=2&xmlName="+xmlName,kvs,{
            headers:{
                'Content-Type': 'application/json'
            }
        }
    )
}
// 获取当前用户的oidxml文件列表
export function getXmlList(){
    return http.get(
        "/oidXml/queryXml"
    )
}

// 删除当前用户的oidxml文件
export function deleteXml(xmlId){
    return http.get(
        "/oidXml/deleteXml?xmlId="+xmlId
    )
}
//返回oid 完整信息，包括所有oid前缀和具体xml文件
export function queryOidInformation(oidId) {
    return http.get(
        "/OID/query/oid/information?id="+oidId
    )
}
