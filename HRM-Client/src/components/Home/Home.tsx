import './Home.scss'
import run from "../../assets/running.png"

function Home() {
  return (
    <div className='home_container'>
      <div className='home_card1'>
        <div className='home_card1_title'>Chiến dịch chạy bộ xuân 2024</div>
        <div className='home_card1_content'>GIẢI CHẠY BỘ XUÂN 2024: Thanh niên rèn luyện sức khoẻ! Bạn đã sẵn sàng cho một hành trình chạy bộ đầy hứng khởi và thách thức? Đến và tham gia ngay `&qout` Giải chạy bộ Xuân 2024 `&qout` tổ chức để trải nghiệm những cung đường chạy siêu đẹp tại khu đô thị Đại học Quốc Gia, đồng thời rèn luyện sức khoẻ tinh thần!</div>
        <button className='home_card1_button'>Xem thêm</button>     
      </div>
      <img src={run} alt='image' className='home_container_image'/>
    </div>
  )
}

export default Home