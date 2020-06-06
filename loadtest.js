import http from 'k6/http';
import { sleep } from 'k6';
import { Rate } from 'k6/metrics';

export let options = {
  vus: 10,
  duration: '30s',
};

const myFailRate = new Rate('failed requests');

export default function() {
  let res = http.get('http://localhost:8080/api/accounts');
  myFailRate.add(res.status !== 200);
}