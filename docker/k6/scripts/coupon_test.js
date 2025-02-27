import http from 'k6/http';
import { check, sleep } from 'k6';
import { SharedArray } from 'k6/data';

export const options = {
  vus: 1000, // 1000명의 유저
  iterations: 1000, // 1000번의 요청만 수행 (중복 방지)
};

// 1~1000번까지의 유저 ID 배열 생성 (각 VU가 하나씩 사용)
const userIds = new SharedArray("userIds", function () {
  return Array.from({ length: 1000 }, (_, i) => i + 1);
});

export default function () {
  // 현재 VU (가상 유저) ID에 따라 유저 ID 선택
  let userId = userIds[__VU - 1]; // VU 1 → userId 1, VU 2 → userId 2 ...

  let res = http.post('http://host.docker.internal:8080/api/v1/coupons',
            JSON.stringify({userId: userId, couponId: 30}),
            {headers: { 'Content-Type': 'application/json' }});

  check(res, {
    '쿠폰 발급 성공': (r) => r.status === 200,
    '쿠폰 발급 실패': (r) => r.status === 400 || r.status === 409,
  });

}
